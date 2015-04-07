## 0.1 (2015-04-08)

### Changed

- **service/AbstractReadExecutor**
	- getReadExecutor
		- retourne un AbstractReadExecutor sans envoi de digest
	- makeDataRequests
		- runnable lancer en maybeExecuteImmediatelyRemovable

- **service/StorageProxy**
	- LocalReadRunnable
		- override equals
		- runMayThrow
			- envoi des messages de suppression à la fin (makeRemoveRequests)

- **db/ReadCommand**
	- override equals

- **net/MessageDeliveryTask**
	- override equals

- **net/MessageIn**
	- override equals

- **net/MessagingService**
	- receive
		- test sur le verb. Si READ, alors executeRemovable
	- enum Verb passage de UNUSED_1 en READ_REMOVE
	- pareil pour verbStages
	- pareil pour verbSerializers

- **service/StorageService**
	- register verb handler pour READ_REMOVE dans le constructeur
	- prepareToJoin
		- startBroadcasting pour le LoadReadBroadcaster

- **db/ReadVerbHandler**
	- doVerb
		- envoi des messages de suppression à la fin (makeRemoveRequests)

- **gms/ApplicationState**
	- état READ_LOAD à la place de X1

- **concurrent/SEPExecutor**
	- onCompletion
		- decremente effectiveLoad si > 0

- **concurrent/AbstractTracingAwareExecutorService**
	- passage du logger de private a protected

- **concurrent/SEPWorker**
	- run
		- logger error en logger info pour un worker sans tâche (devenu possible)

### Added

- **service/AbstractReadExecutor**
	- classe NoDigestReadExecutor
	- fonction makeRemoveRequests

- **concurrent/AbstractTracingAwareExecutorService**
	- classe RemovableFutureTask
	- classe RemovableTraceSessionFutureTask
	- fonction newRemovableTaskFor(Runnable, T)
	- fonction newRemovableTaskFor(Runnable, T, TraceState)

- **concurrent/SEPExecutor**
	- fonction maybeExecuteImmediatelyRemovable

- **db/ReadCommand**
	- fonction createRemoveMessage
		- crée un message de type remove pour cette readcommand

- **concurrent/TracingAwareExecutorService**
	- fonction executeRemovable
	- fonction maybeExecuteImmediatelyRemovable
	- fonction removeCommand

- **concurrent/DebuggableThreadPoolExecutor**
	- fonctions de l'interface à implénenter

- **concurrent/StageManager**
	- stage READ_REMOVE
	- fonctions de l'interface à implémenter pour ExecuteOnlyExecutor

- **concurrent/Stage**
	- stage READ_REMOVE

- **db/ReadRemoveVerbHandler (NEW FILE)**
	- classe ReadRemoveVerbHandler

- **concurrent/SEPExecutor**
	- fonction removeCommand
	- attribut effectiveLoad
	- attribut removedTasks
	- fonction getNonAffectedTasks
	- fonction getRemovedTasks

- **service/LoadReadBroadcaster (NEW FILE)**
	- classe LoadReadBroadcaster
		- gère la relation entre gossiper et le reste de l'application pour la charge de lecture

- **gms/VersionedValue**
	- fonction loadRead

- **locator/MultiHashStrategy (NEW FILE)**
	- classe MultiHashStrategy
		- stratégie de réplication basée sur des fonctions de hachages

- **metrics/SEPMetrics**
	- fonction removedTasks
	- fonction effectiveLoad