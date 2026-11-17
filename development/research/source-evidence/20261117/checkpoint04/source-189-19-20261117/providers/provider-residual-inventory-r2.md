# Collision-provider residual inventory correction (r2)

This immutable correction binds to `build/source-189-19-20261117/providers/provider-residual-inventory.md`, SHA-256 `55871477162491DA0765481E14BE7A4C629260E31E68CF0B692F6D8F17633E1E`. It changes only the disposition of conditional Anvil/Chest mutable-bound observations and the pair-closure gate summary. The r1 source observations and all accepted source reports remain unchanged.

## Anvil and Chest disposition

The exact-source candidates remain valid conditional geometry observations: in 1.8.9 the collision query reads shared mutable block bounds, while 1.9 derives the shape from the queried state/position. Their independent audits did not establish a deterministic, eligible local-player lifecycle that leaves the claimed old bounds at the movement collision query. Identified render, ray-trace, ambient, and non-player cache writers are outside the assigned player-movement scope and do not establish that lifecycle.

Therefore Anvil and Chest are **excluded/failed applicability candidates**, not remaining prerequisites for pair closure. Preserve their conditional source observations and prior REQUEST CHANGES verdicts without promoting them to movement findings. Do not reopen the same lifecycle question unless genuinely new deterministic player-eligible evidence appears.

Bound candidate/audit identities:

- Anvil source report `build/source-189-19-20261117/provider-residual/anvil-orientation-collision-bounds.md`, SHA-256 `fb09fd932a884753ed35b9c014e2e3f01fa2e286e37d5bb40316078969d4afc1`; verdict `provider-residual/anvil-audit/verdict.md` (REQUEST CHANGES; lifecycle gate unclosed).
- Chest source report `build/source-189-19-20261117/provider-residual/chest-neighbor-collision-bounds.md`, SHA-256 `c4591fd10901cb51cd136f389589c9b9366683d834796e4cc16779ace753bf6e`; verdict `provider-residual/chest-audit/verdict.md` (REQUEST CHANGES; lifecycle gate unclosed).

## Preserved source dispositions

- The frozen pane-connection and piston-head-arm operations remain excluded from this residual inventory. Moving-piston response remains separately assigned and is not reopened.
- The accepted Fence/EndPortalFrame query witness remains bounded to a legal ordinary local-player collision query and the changed Fence arm/shape result; it makes no trajectory, state-production, or pair-freeze claim. Its report SHA-256 is `6e2c66b465162fc18a8c23ca3e89707995d3ee8118f57809066622e06236703e`; ACCEPT verdict SHA-256 is `bcbf1090ce814fc7978e9541550a870a4d6e8ca3dae471be757fc75f07989748`.
- The LilyPad collision difference remains accepted as a bounded local-player collision-shape difference: old full-footprint, 1/64-high bounds versus new 1/16-inset, 3/32-high bounds. Correction report SHA-256 `4f0fc4ddc5845c068c9727de0c75e8cc0c9094c729dedf916ac72ce7aed5e3a8`; ACCEPT verdict SHA-256 `9d4ee55815589b07b200e56923963d93a2cf47193369069563a1e381f378ba50`.
- Other provider rows and conditional boundaries remain exactly as described in r1 and their bound reports/audits; this correction does not re-audit them.

## Remaining pair gates

1. **Malformed Door companion state:** the old metadata combination and new matching-half resolution differ conditionally, but no ordinary player-reachable malformed-state witness is established. Keep this conditional/unaccepted; do not treat it as an eligible movement finding without new source evidence of reachability.
2. **Stair state-to-coordinate correspondence:** existing per-stair box-order/clipping evidence is conditional on matching valid resolved shapes. Complete neighbor-state-to-coordinate correspondence remains open.
3. **Pair-wide inventory:** complete tick/provider inventory, dependency closure, and the independent full-pair coverage audit remain required. This residual correction does not freeze the pair.

No implementation readiness or runtime parity is asserted.

## Reference-only corrections

R1 contains two reference transcription errors. Correct them for lookup; no source claim or audit verdict changes:

- In the r1 state-caller audit reference, the incorrectly transcribed SHA-256 `c0f05fc03f7c66f2a4cfa73bd94429a4d809503846ecbedecf548bdfd22a7be` should be `c0f05fc03f7c66f2a4cfa73bd94429a4d809503846ecbedeccf548bdfd22a7be` for `provider-residual/state-provider-callers-audit-r2/verdict.md`.
- In the r1 stair-report reference, the incorrectly transcribed SHA-256 `459d7278de596837fb96f5e447517b6c21ce754172943679424dcdd1c4a92d6` should be `459d7278de596837fb96f5e447517b6c21ce754172943679424dcbdd1c4a92d6` for `provider-residual/stairs-clipping-order.md`. The stair audit-verdict hash in r1 was already correct.

These are identity-only corrections. R2 does not reopen or re-audit the cited technical claims.
