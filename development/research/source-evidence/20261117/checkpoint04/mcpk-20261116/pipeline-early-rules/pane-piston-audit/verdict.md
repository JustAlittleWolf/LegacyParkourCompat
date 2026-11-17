# Independent pane/piston onset audit

Verdict: **bounded inconclusive**

## Immutable input

- Claim packet: `build/mcpk-20261116/pipeline-early-rules/pane-piston-source/pane-piston-onset.md`
- Packet SHA-256: `734ce421d5ac7c388ba21d7ee7e581e38dab5b5a12079aabb5f3337c86f2a6ac`
- Scope: pane collision-geometry provider first checked stable onset; piston-head collision-shape/provider first checked stable onset; operation selectors/order and ordinary player movement reachability. No general pane-behavior review, product code, findings/backlog, lanes or Git history.

## Source identity and hash checks

I independently recomputed the packet SHA-256 and all 47 listed Java-file hashes (seven files x 6 releases plus `BlockProperties.java` in the five 1.9-era releases). Every listed digest matches the local bytes. For 1.8.9 and 1.9.4, the mapping-relative source manifest hashes match their ready markers, both ready markers identify the named release and `ornithe-feather`, and the listed relevant files match manifest entries. The provenance sidecar SHA-256 values match the packet for all six releases. For 1.9 through 1.9.3, each relevant file digest also matches its mapping-local `.decompile-provenance.json` `sources` entry; the provenance inputs identify the respective client metadata and Feather Gen2 build.2 artifact.

The remaining acceptance gate is canonical identity authentication for the 1.9–1.9.3 source rows. Those local trees have no root ready marker, no root provenance sidecar, and the packet says the read-only identity locator omitted those exact rows. Their hidden provenance and matching hashes establish local consistency, but do not resolve the known canonical-identity limitation. Since these middle versions are necessary to establish the checked onset across the assigned series, this audit does not accept either onset claim as authenticated source evidence. No source regeneration or online mapping lookup was performed.

## Bounded source observations

- **Pane onset is consistent with the packet.** In 1.8.9, `PaneBlock.addCollisions` selects and mutates block bounds for the connected axes, then delegates collision addition (lines 62–91). In 1.9, `PaneBlock.addCollisions` resolves virtual connection properties and adds the center plus selected arm boxes; `getShape` also resolves those properties and selects from `SHAPES` (lines 52–80). 1.9.1–1.9.2 have the same pane-file hash as 1.9; 1.9.3–1.9.4 share another hash, whose implementation still has those providers. This supports 1.9 as the earliest *checked* release showing this provider form, conditional on source identity acceptance.
- **Piston onset and order are consistent with the packet.** In 1.8.9, `addCollisions` invokes head-shape setup, submits it via `super.addCollisions`, invokes arm-shape setup, submits it, then resets bounds to a unit cube (`PistonHeadBlock.java:87–92`). The facing selector is `FACING`; the arm switch writes facing-specific bounds, e.g. NORTH z=0.25..1.0 and DOWN y=0.25..1.0 (`:95–119`). In 1.9, `getShape` selects a head box by `FACING`, and `addCollisions` adds the state shape before the arm (`:24–35,45–67`); the NORTH arm reaches z=1.25 and DOWN arm reaches y=1.25. `TYPE` and `SHORT` are not read by those selectors. 1.9.1–1.9.2 share the 1.9 file digest; 1.9.3–1.9.4 share the later digest, and 1.9.4 retains the same selector and submission order. Thus 1.9 is earliest among the checked releases for the changed provider form, conditional on source identity acceptance.
- **Player reachability is ordinary collision movement.** In 1.8.9 `World.getCollisions` dispatches block collision generation through the block at each intersecting position (`World.java:891–921`); 1.9+ dispatches through `BlockState.addCollisions` (`1.9.4 World.java:899–933`), whose `StateDefinition` implementation forwards to the block (`1.9 StateDefinition.java:353–354`). `Entity.move` obtains collisions for the expanded entity box and clips Y, X, then Z (`1.8.9 Entity.java:439–459`; `1.9.4 Entity.java:509–532`). `LivingEntity` calls inherited `move` in its movement routine, and `PlayerEntity extends LivingEntity` in both 1.8.9 and 1.9. This establishes ordinary source-level player reachability only; it does not predict a trajectory or establish runtime parity.

## Limits and resolution

The earliest release supported by inspected code is 1.9 for both provider forms, but this is a conditional source observation rather than an accepted onset. To resolve the verdict, the canonical identity owner must publish or identify authenticated exact-version source rows for 1.9, 1.9.1, 1.9.2 and 1.9.3 (or otherwise close the known canonical identity gate). Then this bounded hash and call-path check can be reused if those files and claim packet remain unchanged. No behavioral runtime claim is made.

