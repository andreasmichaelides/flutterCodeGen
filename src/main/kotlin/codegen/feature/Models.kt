package codegen.feature

import codegen.model.MustacheModel
import com.github.mustachejava.Mustache


data class FeatureViewModel(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    override val filePath: String,
    override val featureUnderScore: String,
    val featureName: String,
    val featureNameLowerCase: String
) : MustacheModel(className, packageName, corePackageName, mustache, filePath, featureUnderScore)

data class FeatureUiModel(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    override val filePath: String,
    override val featureUnderScore: String,
    val featureName: String,
    val featureNameLowerCase: String
) : MustacheModel(className, packageName, corePackageName, mustache, filePath, featureUnderScore)

data class FeatureWidget(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    override val filePath: String,
    override val featureUnderScore: String,
    val featureName: String,
    val featureNameLowerCase: String,
    val featureNameCamelCaseLower: String,
    val featureNameLowerUnderscore: String
) : MustacheModel(className, packageName, corePackageName, mustache, filePath, featureUnderScore)

data class Inputs(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    override val filePath: String,
    override val featureUnderScore: String,
    val featureName: String,
    val featureNameLowerCase: String
) : MustacheModel(className, packageName, corePackageName, mustache, filePath, featureUnderScore)

data class Actions(
    override val className: String,
    override val packageName: String,
    override val corePackageName: String,
    override val mustache: Mustache,
    override val filePath: String,
    override val featureUnderScore: String,
    val featureName: String,
    val featureNameLowerCase: String
) : MustacheModel(className, packageName, corePackageName, mustache, filePath, featureUnderScore)