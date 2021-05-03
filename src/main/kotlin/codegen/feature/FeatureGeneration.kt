package codegen.feature

import codegen.model.MustacheModel
import codegen.tools.*
import com.github.mustachejava.DefaultMustacheFactory
import java.io.File
import java.io.StringWriter


fun createFeature(
    featureName: String,
    packageName: String,
    corePackageName: String,
    mustacheFactory: DefaultMustacheFactory
) {
    val featureNameUnderscore = featureName.toLowerCameCase().toLowerUnderscore()
    val featurePackagePath = "lib/features${File.separator}$featureNameUnderscore"

    val featurePresentationPath = "$featurePackagePath${File.separator}presentation".toLowerCameCase().toLowerUnderscore()


    val featureMustacheModels = listOf(
        FeatureViewModel(
            "${featureName}ViewModel",
            packageName,
            corePackageName,
            mustacheFactory.compile("featureViewModel.mustache"),
            featurePresentationPath,
            featureNameUnderscore,
            featureName,
            featureName.toLowerCase()
        ),
        FeatureWidget(
            "${featureName}Widget",
            packageName,
            corePackageName,
            mustacheFactory.compile("widget.mustache"),
            featurePresentationPath,
            featureNameUnderscore,
            featureName,
            featureName.toLowerCase(),
            featureName.toLowerUnderscore(),
            featureName.toLowerCameCase()
        ),
        FeatureUiModel(
            "${featureName}UiModel",
            packageName,
            corePackageName,
            mustacheFactory.compile("uiModel.mustache"),
            featurePresentationPath,
            featureNameUnderscore,
            featureName,
            featureName.toLowerCase()
        ),
        Inputs(
            "Inputs",
            packageName,
            corePackageName,
            mustacheFactory.compile("inputs.mustache"),
            "$featurePresentationPath/inputs",
            featureNameUnderscore,
            featureName,
            featureName.toLowerCase()
        ),
        Actions(
            "Actions",
            packageName,
            corePackageName,
            mustacheFactory.compile("actions.mustache"),
            featurePresentationPath,
            featureNameUnderscore,
            featureName,
            featureName.toLowerCase()
        )
    )

    featureMustacheModels.forEach {
        if (!doesFileExist(it.filePath, it.className.toLowerCameCase().toLowerUnderscore(), ".dart")) {
            val kotlinClassWriter = getFileWriter(it.filePath, it.className.toLowerCameCase().toLowerUnderscore(), ".dart")
            writeMustacheTemplate(it.mustache, it, kotlinClassWriter)
        } else {
            System.out.println("File ${it.className.toLowerCameCase().toLowerUnderscore()} exists, will not create")
        }
    }
}

val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()
val snakeRegex = "_[a-zA-Z]".toRegex()

// String extensions
fun String.camelToSnakeCase(): String {
    return camelRegex.replace(this) {
        "_${it.value}"
    }.toLowerCase()
}

fun String.snakeToLowerCamelCase(): String {
    return snakeRegex.replace(this) {
        it.value.replace("_","")
            .toUpperCase()
    }
}

fun String.snakeToUpperCamelCase(): String {
    return this.snakeToLowerCamelCase().capitalize()
}