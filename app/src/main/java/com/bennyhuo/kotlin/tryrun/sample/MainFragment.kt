package com.bennyhuo.kotlin.tryrun.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bennyhuo.kotlin.tryrun.catch
import com.bennyhuo.kotlin.tryrun.tryRun
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by benny.
 */
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            tryCatchButton.setOnClickListener {
                lifecycleScope.launch {
                    try {
                        doHardWork()
                        resultText.text = "Done with 'try ... catch'"
                    } catch (e: Exception) {
                        resultText.text = e.message
                    }
                }
            }

            tryRunButton.setOnClickListener {
                lifecycleScope.launch {
                    tryRun {
                        doHardWork()
                        resultText.text = "Done with 'try ... catch'"
                    } catch { e: Exception ->
                        resultText.text = e.message
                    }
                }
            }
        
    }

    suspend fun doHardWork() {
        delay(5000)
    }
}