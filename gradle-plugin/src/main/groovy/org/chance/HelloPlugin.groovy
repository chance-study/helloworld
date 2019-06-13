package org.chance

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by GengChao on 2016/9/27.
 */
public class HelloPlugin implements Plugin<Project> {
    void apply(Project project) {

        project.gradle.addListener(new TimeListener())

        project.extensions.create('pluginExt', PluginExtension)
        project.pluginExt.extensions.create('nestExt',PluginNestExtension)
        project.task('customTask',type:CustomTask)

        project.task('testTask') << {
            println "Hello gradle plugin"
        }
    }
}
