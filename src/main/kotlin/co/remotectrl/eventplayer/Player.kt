package co.remotectrl.eventplayer

class Player<TModel: Aggregate<TModel>> {
    fun playFor(evts: Array<PlayEvent<TModel>>, model: TModel) {
        for(evt in evts){
            evt.applyTo(model)
        }
    }

}