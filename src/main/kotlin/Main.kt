import codegen.feature.createFeature
import com.github.mustachejava.DefaultMustacheFactory
import com.google.gson.Gson
import parser.ModelParserImpl
import java.io.File


fun main(args: Array<String>) {

    val modelParser = ModelParserImpl(Gson())

    val featureName = args[0]

    val mustacheFactory = DefaultMustacheFactory(File("templates"))

    val propertiesString = File("projectGeneratorProperties.json").readText()
    val properties = modelParser.parseToModel(propertiesString, Properties::class.java)

    createFeature(featureName, properties.packageName, properties.corePackageName, mustacheFactory)
}

data class Properties(
    val packageName: String,
    val corePackageName: String
)