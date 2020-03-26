package com.me.guanpj.libnavcompiler

import com.alibaba.fastjson.JSONObject
import com.google.auto.service.AutoService
import com.me.guanpj.libnavannotation.ActivityDestination
import com.me.guanpj.libnavannotation.FragmentDestination
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.FileObject
import javax.tools.StandardLocation

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.me.guanpj.libnavannotation.FragmentDestination", "com.me.guanpj.libnavannotation.ActivityDestination")
class NavProcessor : AbstractProcessor() {
    private val outPutFileName = "destination.json"
    private var messager: Messager? = null
    private var filer: Filer? = null

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        messager = processingEnv?.messager
        filer = processingEnv?.filer
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        val fragmentElements = roundEnv!!.getElementsAnnotatedWith(FragmentDestination::class.java)
        val activityElements = roundEnv.getElementsAnnotatedWith(ActivityDestination::class.java)

        if (fragmentElements.isNotEmpty() || activityElements.isNotEmpty()) {

            var destMap: HashMap<String, JSONObject> = HashMap()
            handleDestination(fragmentElements, FragmentDestination::class.java, destMap)
            handleDestination(activityElements, ActivityDestination::class.java, destMap)

            val resource: FileObject? =
                filer?.createResource(StandardLocation.CLASS_OUTPUT, "", outPutFileName)
            val resourcePath = resource?.toUri()?.path
            messager?.printMessage(Diagnostic.Kind.NOTE, "resourcePath:$resourcePath")

            val appPath = resourcePath?.substring(0, resourcePath.indexOf("app") + 4)
            val assetsPath = appPath + "src/main/assets/"

            val outPutFile = File(assetsPath, outPutFileName)
            if (outPutFile.exists()) {
                outPutFile.delete()
            }
            outPutFile.createNewFile()

            val content = JSONObject.toJSONString(destMap)
            outPutFile.writeText(content)
        }

        return true
    }

    private fun handleDestination(elements: Set<Element>?,
                                  annotationClz: Class<out Annotation>,
                                  destMap: HashMap<String, JSONObject>) {
        elements?.forEach {
            val typeElement = it as? TypeElement
            var clzName = typeElement?.qualifiedName.toString()
            val id = Math.abs(clzName.hashCode())
            var pageUrl = ""
            var needLogin = false
            var asStarter = false
            var isFragment = false

            val annotation = it.getAnnotation(annotationClz)
            if (annotation is FragmentDestination) {
                pageUrl = annotation.pageUrl
                asStarter = annotation.asStarter
                needLogin = annotation.needLogin
                isFragment = true
            } else if (annotation is ActivityDestination) {
                pageUrl = annotation.pageUrl
                asStarter = annotation.asStarter
                needLogin = annotation.needLogin
                isFragment = false
            }

            if (destMap.containsKey(pageUrl)) {
                messager!!.printMessage(Diagnostic.Kind.ERROR, "不同的页面不允许使用相同的pageUrl：$annotationClz")
            } else {
                val jsonObject = JSONObject()
                jsonObject["id"] = id
                jsonObject["needLogin"] = needLogin
                jsonObject["asStarter"] = asStarter
                jsonObject["pageUrl"] = pageUrl
                jsonObject["className"] = clzName
                jsonObject["isFragment"] = isFragment
                destMap[pageUrl] = jsonObject
            }
        }
    }
}