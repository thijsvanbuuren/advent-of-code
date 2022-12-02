package advent.util

import org.reflections.Reflections
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder

inline fun <reified T : Annotation> listClasses(packageName: String = "advent") = Reflections(
    ConfigurationBuilder()
        .filterInputsBy(FilterBuilder().includePackage(packageName))
        .setUrls(ClasspathHelper.forPackage(packageName))
).getTypesAnnotatedWith(T::class.java).map { it.kotlin }
