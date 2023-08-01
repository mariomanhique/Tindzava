package com.example.moztrivia.repositories.firebaseRepository.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

suspend fun <T> Task<T>.await():T{
    return suspendCancellableCoroutine {coroutine->
        addOnCompleteListener {task->
            if(task.exception!=null){
                coroutine.resumeWithException(task.exception!!)
            }else{
                coroutine.resume(task.result,null)
            }

        }

    }
}
