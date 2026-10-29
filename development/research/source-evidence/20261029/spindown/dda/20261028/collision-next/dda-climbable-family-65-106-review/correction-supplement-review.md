# Correction supplement review

## Verdict: ACCEPT

The supplement closes the sole issue recorded in the prior `REVISE` review. Its corrected statement matches canonical 26.2 source: Ladder registration omits `noCollision()` (`Blocks.java:1442–1446`), the collision property defaults true (`BlockBehaviour.java:982–986`), and the inherited collision method delegates to the state shape when enabled (`BlockBehaviour.java:327–329`); Ladder supplies a facing-indexed shape (`LadderBlock.java:41–44`). Vine registration calls `noCollision()` (`Blocks.java:2429–2441`), that method clears `hasCollision` (`BlockBehaviour.java:1094–1097`), and Vine's visible shape remains a separate method (`VineBlock.java:54–72`).

The supplement preserves the original finding and prior review as immutable history, binds both by their exact hashes, and limits its correction to the identified summary contradiction. No DDA or runtime claim is introduced. The original `REVISE` remains the disposition of the original finding revision; this `ACCEPT` applies to the correction supplement and closes that issue for the corrected bundle.

Supplement reviewed: `correction-supplement.md`, SHA-256 `bfc2c148062f118d7ad92182c959085fd8d8ec28cf105ec89a547fe62e7e4b8a`.

Prior review preserved: `review.md`, SHA-256 `a60b1c850afb9221f2e277e48c506bc48ab66e9d0a4b6b1a262dd5f5eadabad1`.
