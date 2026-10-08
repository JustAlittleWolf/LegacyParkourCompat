# WORLD-01 independent review — 2026-10-08

## Snapshot identity

- Owner finding: commit `1dbafbb7899c57ddf1718e421aa2c32e283fc7c7`, path `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-01-trapdoor-ladder-climbing.md`, SHA-256 `23b3f17aafc35e7ee8589924f08dcc0e6a9a1a8cca863f912b0301c12967c72c`.
- No WORLD-01 snapshot event appears in the owner run at that revision. The pair run remains partial; this decision does not infer pair completeness.
- Source manifests: A `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; B `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. Independently checked cited hashes: A `LivingEntity.java` `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`, `TrapdoorBlock.java` `e00c4fdfa234ac5cf265816fbbe088709668cd5a44eb537a1f391ce2f5f795ba`, `LadderBlock.java` `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106`; B `LivingEntity.java` `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`, `TrapdoorBlock.java` `bbfbeac22de7e72b55736a35c29c10f372df1d846dd01cd16d7ee2a0913b2339`, `LadderBlock.java` `f411d494a4f8c87b2b23d182527713d3b0c2fad4da329b82b84ea301a9d65fcb`.
- Artifact identity: immutable `feather-r1-2026-10-07` snapshots, A `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`, B `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. Original derived mapped JARs are unavailable; their identity/equivalence is unproven.

## Decision: accept, bounded to the predicate and travel branch

Both versions register ladders and trapdoors. A has horizontal `FACING` on both and boolean `OPEN` on trapdoors; B has corresponding properties. In A, `LivingEntity.isClimbing` returns true for a ladder or vine at the floored position, except for spectator players. B retains that rule and adds an open trapdoor case requiring a ladder directly below with matching horizontal facing.

Both paired `LivingEntity.moveRelative` bodies consult `isClimbing` during travel and apply the climb horizontal clamp, fall-distance reset, downward-velocity clamp, and player-sneak descent handling. When B's added predicate holds, it selects this existing travel branch while A does not. The compared arrangement uses blocks and state properties present in both versions.

## Limits

- This accepts the source-level predicate and direct travel-branch difference only. It makes no shape-equivalence, trajectory, or first-changed-release claim; that release remains unknown within `(1.8.9, 1.9.4]`.
- Runtime validation was not performed; original mapped-JAR identity/equivalence remains unproven.
