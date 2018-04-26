import org.amshove.kluent.AnyException
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

data class StubModel(override val id: AggregateId<StubModel>, var changeVal: Boolean, var sum: Int = 0) : Aggregate<StubModel>()

data class StubChangeCommand(val b: Boolean) : PlayCommand<StubModel>() {
    override fun validate(model: StubModel) {
        if (model.changeVal == b) {
            throw Exception("Invalid Input")
        }
    }

    override fun getEvent(id: AggregateId<StubModel>, version: Int): PlayEvent<StubModel> {
        return StubChangedEvent(id = id, version = version, b = b)
    }
}

data class StubChangedEvent(override val id: AggregateId<StubModel>, override val version: Int, val b: Boolean) : PlayEvent<StubModel>() {
    override fun applyChangesTo(model: StubModel) {
        model.changeVal = b
    }
}

class EventPlayerTest : Spek({

    describe("EventPlayer Aggregate") {

        val idVal = 1
        val id = AggregateId<StubModel>(Value = idVal)
        val model = StubModel(id = id, changeVal = false)

//todo: identifywhy on is hidden in intellij test runner
//        on("Command") {

            val cmd = StubChangeCommand(b = true)

            it("should return a resulting PlayEvent abstraction on successful execution") {

                val evt: PlayEvent<StubModel> = cmd.executeOn(model)

                evt.id.Value shouldEqual idVal
                evt.version shouldEqual 1
                (evt as StubChangedEvent).b shouldEqual cmd.b
            }

            it("should try to validate command input on execution") {
                { val blah = StubChangeCommand(b = false).executeOn(model) } shouldThrow AnyException
            }
//        }

//        on("Event") {

            val evt = StubChangedEvent(id = id, version = 1, b = true)

            it("should apply the event to the mutable values of the model") {

                evt.applyTo(model)

                model.changeVal shouldEqual evt.b
                model.latestVersion shouldEqual evt.version
            }
//        }

//        on("Player") {

            val player = Player<StubModel>()

            data class StubAddedEvent(override val id: AggregateId<StubModel>, override val version: Int, val addVal: Int) : PlayEvent<StubModel>() {
                override fun applyChangesTo(model: StubModel) {
                    model.sum += addVal
                }
            }

            it("should play over a list of events") {
                val evts = Array(3, { i -> StubAddedEvent(id = id, version = i, addVal = i) as PlayEvent<StubModel> })
                player.playFor(evts, model)

                model.sum shouldEqual 2 + 1
            }
//        }
    }
})