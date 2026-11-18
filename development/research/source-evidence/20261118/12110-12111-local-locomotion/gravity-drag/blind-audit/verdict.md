# Independent claim review

Reviewed the two authorized author revisions against the frozen blind audit only.

- `comparison.md` raw SHA-256: `570200e4ed673fd5164cdd7172369ec4ddd5748c7245c6bf72d54994992dcd2b`.
- `comparison-r1.md` raw SHA-256: `5a2a92b0fcd7b51c2255d2b4d5ab1cb8317694c7ac1537ff779a8f4629c5ae11`.
- Bound freeze raw SHA-256: `5e9a566056a6bb78bd809043632e548c3e0b22b16202d4082b53d15c18941610`.

## Verdict

**Accept `comparison-r1.md` for the bounded ordinary dry, non-riding gravity/drag equality claim when bound to the frozen audit.** Its corrected source-manifest statement matches the freeze: both version-root source-manifest byte hashes match their ready records, and the consumed `Entity.java`, `LivingEntity.java`, and `LocalPlayer.java` hashes match the listed manifest entries. The original `comparison.md` is superseded: its claim that these manifests are absent contradicts the frozen, hash-verified canonical-root evidence.

The corrected witness and ordering claims agree with the freeze: local-player tick reaches the direct travel path under the stated gates; ordinary dry travel reaches air travel; Levitation precedes gravity; Slow Falling and NoGravity are routed through the same effective/common gravity getters; the client unloaded-chunk fallback remains in place; and the first movement precedes gravity adjustment and final axis drag. The frozen audit independently recorded equal method bodies for these operations and the friction-discard getter, with no supported positive difference in scope.

One evidence limitation remains in `comparison-r1.md`: it cites the `Player.travel` wrapper but its consumed-hash list omits `Player.java`. The freeze supplies the missing binding (`Player.java` SHA-256: 1.21.10 `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`; 1.21.11 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`) and records equal extracted wrapper bodies. This is not a blocker for the claim as bound here, but the author artifact would need those identities to stand alone.

## Limits

This verdict accepts only the frozen bounded equality claim and the corrected artifact's consistency with it. It does not establish complete pair coverage, an intermediate patch boundary, runtime parity, or any out-of-scope movement behavior. The frozen audit was preserved unchanged.