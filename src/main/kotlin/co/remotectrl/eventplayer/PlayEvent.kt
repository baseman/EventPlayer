package co.remotectrl.eventplayer

abstract class PlayEvent<TModel : Aggregate<TModel>> {

    abstract val id: EventId<TModel>
    abstract val version: Int
    abstract val aggregateId: AggregateId<TModel>

    protected abstract fun applyChangesTo(model: TModel)
    fun applyTo(model: TModel) {
        applyChangesTo(model)
        model.latestVersion = version
    }
}

class EventId<TModel>(val Value: Int) where TModel : Aggregate<TModel>