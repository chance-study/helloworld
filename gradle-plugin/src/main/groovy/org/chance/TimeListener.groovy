package org.chance

import org.apache.tools.ant.BuildEvent
import org.apache.tools.ant.BuildListener
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.tasks.TaskState
import org.gradle.util.Clock

/**
 * Created by GengChao on 2016/9/27.
 */
class TimeListener implements TaskExecutionListener,BuildListener {

    private Clock clock
    private times = []

    @Override
    void buildStarted(BuildEvent buildEvent) {

    }

    @Override
    void buildFinished(BuildEvent buildEvent) {
        println "Task spend time:"
        for(time in times) {
            if(time[0] >= 50 ) {
                printf "%7sms %s\n", time
            }
        }
    }

    @Override
    void targetStarted(BuildEvent buildEvent) {

    }

    @Override
    void targetFinished(BuildEvent buildEvent) {

    }

    @Override
    void taskStarted(BuildEvent buildEvent) {

    }

    @Override
    void taskFinished(BuildEvent buildEvent) {

    }

    @Override
    void messageLogged(BuildEvent buildEvent) {

    }

    @Override
    void beforeExecute(Task task) {
        clock = new Clock()
    }

    @Override
    void afterExecute(Task task, TaskState taskState) {
        def ms = clock.timeInMs
        times.add([ms, task.path])
        task.project.logger.warn "${task.path} spend ${ms}ms"
    }
}
