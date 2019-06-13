package gradle_plugin

import com.sun.org.apache.xalan.internal.xsltc.compiler.Copy
import org.gradle.api.Project
import org.gradle.api.Plugin

/**
 * @author Administrator
 * Created: Tue Sep 27 10:46:37 CST 2016
 */
class Gradle_pluginPlugin implements Plugin<Project> {
   void apply (Project project) {
      project.convention.plugins.Gradle_pluginPlugin = new Gradle_pluginPluginConvention()

      project.getConvention().getPlugins().put("testPlugin",Gradle_pluginPlugin)

      // add your plugin tasks here.
      project.getTasks().add("hello",Gradle_pluginPlugin.class)

//      Copy copyTask = project.getTasks().add("copyAll", Copy.class)
//      copyTask.dependsOn(project.getPath +":jar")
//      copyTask.from(project.getConfigurations().getByName("runtime"));
//      copyTask.from(project.getBuildDir().getPath() + "/classes/main");
//      copyTask.from(project.getBuildDir().getPath() + "/classes/resources");
//      copyTask.into(project.getBuildDir().getPath() + "/dist");
   }
}