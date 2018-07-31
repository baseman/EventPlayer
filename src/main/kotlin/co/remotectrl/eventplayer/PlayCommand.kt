package co.remotectrl.eventplayer

interface PlayCommand<TModel : Aggregate<TModel>> {

    fun getEvent(aggregateId: AggregateId<TModel>, version: Int): PlayEvent<TModel>

    fun validate(model: TModel)

    fun executeOn(model: TModel): PlayEvent<TModel> {
        validate(model)
        return getEvent(model.legend.aggregateId, model.legend.latestVersion + 1)
    }
}