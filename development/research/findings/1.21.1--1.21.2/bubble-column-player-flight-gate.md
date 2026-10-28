# Bubble-column player response while abilities flight is active

Mojmap 1.21 and 1.21.1 Player have no overrides for Entity.onAboveBubbleCol/onInsideBubbleColumn, so inherited velocity responses run even when Abilities.flying is true. Mojmap 1.21.2 adds Player overrides that call the inherited methods only when flying is false; 1.21.3 retains those bodies. BubbleColumnBlock.entityInside reaches both callbacks. The flight gate changes the direct player velocity response and the inside callback's fall-distance reset.

Exact 1.21 and 1.21.1 Player source is byte-identical under the independently audited alias inventory. The bounded implementation restores the inherited response for these two releases by adapting only the flying-field reads in the current Player callbacks, keeping their original super calls and impulse math. Earlier profiles and 1.21.2 onward remain native for this operation. Bubble contact classification and callback eligibility remain independently resolved operations.

Exact source references, file hashes, alias comparison and independent audits are retained in development/research/source-evidence/20261028/bubble-player-flight/. This is a source-confirmed callback gate, not runtime or input-only trajectory parity.
