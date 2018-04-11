abstract class PlayCommand<TModel : Aggregate<TModel>> {

    abstract fun getEvent(id: AggregateId<TModel>, version: Int): PlayEvent<TModel>

    abstract fun validate(model: TModel)

    fun executeOn(model: TModel): PlayEvent<TModel> {
        validate(model)
        return getEvent(model.id, model.latestVersion + 1)
    }
}