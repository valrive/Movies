package com.udemy.startingpointpersonal.ui.view.popularMovs.adapter

sealed interface Action {
    class Click(val item: Any) : Action
    class Share(val item: Any) : Action
    class Favorite(val item: Any) : Action
    class Delete(val item: Any) : Action
}