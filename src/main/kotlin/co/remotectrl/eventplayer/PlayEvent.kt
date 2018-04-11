abstract class PlayEvent<TModel : Aggregate<TModel>> {

    abstract val id: AggregateId<TModel>
    abstract val version: Int
    protected abstract fun applyChangesTo(model: TModel)
    fun applyTo(model: TModel) {
        applyChangesTo(model)
        model.latestVersion = version
    }
}