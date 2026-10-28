# Water-first suppression of lava depth and current

Mojmap 1.16 and 1.16.1 Entity.updateInWaterStateAndDoFluidPushing clears fluidHeight, processes WATER, then returns immediately when the legacy water result is true. In that case LAVA depth remains zero and no lava current applies. In 1.16.2 the lava pass also runs after water. These wrapper bodies and the relevant alias identities are independently source-audited.

The legacy water gate uses a 0.001-deflated player box, floor minima and ceil-exclusive maxima, WATER membership, inclusive fluidTop >= box.minY, old Y overlap/empty-cell semantics, and the Boat exclusion. ClientLevel.hasChunk always returns true, so no real loaded-XZ condition may be introduced. Old post-move refresh affects WATER only; the baseTick lava decision therefore remains in force through later movement.

Current 26.2 scans all fluid trackers together, so the source-bounded adapter latches an independently computed legacy water result at the baseTick fluid phase. For selected 1.16/1.16.1, a positive result masks public Entity.getFluidHeight(LAVA) to zero and suppresses LAVA current application, including modern post-move refreshes. It does not mutate internal tracker heights/eyes, change WATER, or implement the separate historical callback-based isInLava predicate. Earlier and 1.16.2+ profiles remain native for this operation.

Exact source references, state-consumer inventory, independent review and lifecycle qualifications are retained in development/research/source-evidence/20261028/water-first-lava-results/. Water-current numerics, eye-fluid sampling, separate lava presence, and dry-baseTick duplicate refresh are independent operations. Compilation does not establish runtime parity.
