package co.remotectrl.eventplayer

class `Player.kt`<TModel: Aggregate<TModel>> {
    fun playFor(evts: Array<PlayEvent<TModel>>, model: TModel) {
        for(evt in evts){
            evt.applyTo(model)
        }
    }

}