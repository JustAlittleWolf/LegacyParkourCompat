# Movement research workflows

Campaign coordinators read [orchestration](orchestration/README.md) for scheduling and handoffs. Artifact owners read [source preparation](movement-discovery/source-preparation.md) before preparing sources or resolving mapping alignment. Source workers consume published artifacts read-only.

1. [Difference discovery](movement-discovery/README.md): compare two exact Minecraft releases and produce a source-backed, fine-grained difference catalog. Start here.
2. [Fix implementation](fix-implementation/README.md): implement one source-backed finding at a time as a versioned Java change behind a reusable Minecraft hook.
3. Testing and validation: separate future workflow; consumes the catalog and checks behavior in the TAS lab.

Discovery completion means exact-source inventories and bounded slices are evidence-backed, dependencies are closed, the source-only report was frozen before implementation reconciliation, and an independent reviewer passed a full-tick coverage audit. A populated catalog, worker completion, narrow unchanged travel path, or build is not discovery completion. Implementation coverage is tracked per frozen finding and remains separate from runtime validation; a build does not establish movement parity.
