# Anvil writer lifecycle in exact 1.8.9 and 1.9

This supplements the [mutable bounds finding](../../../findings/1.8.9--1.9.4/anvil-mutable-collision-bounds.md) with a conditional single-tick source trace. In exact 1.8.9, the downward tick-scheduled camera pick at the X-facing Anvil writes the shared bounds before entity movement. If they persist, the later Z-facing Anvil at (1,1,1) is outside the swept Z interval for the reviewed +X move. The state-correct Z box intersects the query and limits the first X pass to approximately .025. Exact 1.9 selects the queried state's axis box independently of the pick.

The witness requires dry weather, stable loaded states, a successful camera hit, no intervening Anvil writer, and no active or queued chunk worker writing any Anvil during the interval. Rain samples, model compilation, other entity or action paths and earlier frame state matter to the lifecycle. Main-thread tick order alone cannot establish the last writer. The revision 2 trace supersedes the initial lifecycle claims and incorporates the independent rain and worker correction.

In exact 26.2, `Minecraft` retains the final `LocalPlayer.raycastHitResult` result. It does not carry the complete provider visitation and miss sequence or renderer and chunk compilation writes. Observing that result alone cannot attest the old last writer. The existing V1_8_9 native fence stays in place. Broader renderer scheduling, provider visitation, applicability and runtime parity remain unresolved. No historical world state or writer model is introduced.

## Immutable archive

- [anvil-writer-lifecycle.md](anvil-writer-lifecycle.md), raw SHA-256 `53b3030eaee6c5158cafa8e739e20d78920064d7c8f355f348691b7a51496c93`.
- [anvil-writer-lifecycle-r2.md](anvil-writer-lifecycle-r2.md), raw SHA-256 `ef6d29ebc0d7511510e80f2d3fb1a6ad76269b1968641501b003e06e194ef5d2`.
- [verdict.md](verdict.md), raw SHA-256 `5282b7816c490ae6dc83c17c2cc4875a29d7c5994d236f947f7a7af8f8ab60ec`.

The local canonical 1.9 hidden provenance SHA-256 is `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`. Its targeted entries were rehashed alongside those in the 1.8.9 ready source manifest.
