package com.aklymov.mynetdaity.testapp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aklymov.mynetdaity.feature_clients_list.view.ClientsListFragment
import com.aklymov.mynetdaity.testapp.R
import com.aklymov.mynetdaity.testapp.di.MainModule
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.android.subDI

internal class MainActivity : AppCompatActivity(), DIAware {

    override val di: DI by subDI(closestDI()) {
        import(MainModule.instance)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.flMainContainer, ClientsListFragment())
                .commitNow()
        }
    }
}
