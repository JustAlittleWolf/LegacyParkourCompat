# Early boat rider transform source audit

**COMPLETED** — independently verified the bounded local-player, sole-passenger placement and rider-yaw claims against canonical local `ornithe-feather` decompilations. The audited comparison is `early-boat-rider-transform-source/comparison.md`, SHA-256 `2e120c8d3517ad4d229a82464397623cc7ae5601a3e9a77196c2a903adb4e3b4`.

## Findings

- In 1.8.9, the one-rider `BoatEntity.updateRiderPositon()` guard and operation order are as stated: compute `cos(yaw * PI / 180.0) * 0.4`, then `sin(...) * 0.4`, then call `setPosition(x + d, y + getMountHeight() + rider.getRideHeight(), z + e)`. The boat mount height is `-0.3`; `PlayerEntity.getRideHeight()` is `-0.35`. The ordinary player placement hook has no yaw writes. `Entity.rideTick()` calls the rider tick before boat placement; its yaw-delta bookkeeping does not directly assign rider yaw.
- From 1.9 through 1.9.4, `BoatEntity.updateRiderPositon(Entity)` checks `hasPassenger`, leaves horizontal `f` at `0.0F` for a sole passenger, rotates the zero vector, then writes position. For an active boat and ordinary player, it evaluates `(float)((-0.1) + (-0.35))`; `setPosition` receives boat y plus this float. Then the hook adds boat yaw velocity to rider yaw and head yaw, calls `copyEntityData`, sets body yaw to boat yaw, wraps rider yaw minus boat yaw, clamps to `[-105.0F, 105.0F]`, adjusts last yaw and yaw by the clamped-minus-wrapped difference, and sets head yaw to corrected rider yaw. The removed-boat vertical branch and multi-passenger offset are correctly excluded from the stated witness.
- Concrete finite witness: active boat at `(10.0, 20.0, 30.0)`, yaw `0.0F`; ordinary local player is its only passenger and remains mounted through the player tick. In 1.8.9, the placement is `(10.4, 19.349999999999998, 30.0)` under the source's ordered double operations. In 1.9+, it is `(10.0, 19.55000001192093, 30.0)` because the seat offset is cast to float before being added to y. Thus “+0.2” is the nominal seat shift; its exact evaluated difference in this witness is `0.2000000119209311`.
- Yaw witness at hook entry: rider yaw and head yaw `100.0F`, boat yaw `0.0F`, boat yaw velocity `10.0F`. The 1.9+ hook leaves rider yaw and head yaw `105.0F` after clamp/copy and sets body yaw to `0.0F`; the 1.8.9 boat placement hook leaves those yaw fields untouched. This describes the bounded hook effect only, after the rider tick.

## Evidence identity

All six canonical source directories are under `decompiled_minecraft/<version>/ornithe-feather/`; source-side provenance identifies the expected releases/mapping artifacts. The 1.9–1.9.3 nested `.decompile-provenance.json` format has no top-level release or namespace fields, but its recorded version-specific metadata inputs and per-file `sources` hashes bind the corresponding release and source files. The 1.8.9 and 1.9.4 outer provenance sidecars explicitly name versions and `ornithe-feather`.

Verified `BoatEntity.java` SHA-256 values:

- 1.8.9: `77ef72c28024b754ff76f2c67e14dd61832394bac2bd7b62b29708560e588da2`
- 1.9–1.9.2: `950cc19ce2627f02d619658eab6560b40758149eacd2e62534653666ad7eb758`
- 1.9.3–1.9.4: `3d41b9df0ef0d36158105235c92b35dac4b51070476e7ef688520961d1d56e57`

The audited classes and method names are present in each patch-level source. The 1.9.3/1.9.4 line shift does not change the placement or yaw helper bodies. The source claim that the change is bounded after 1.8.9 and present by 1.9 is supported; the exact earlier introduction point is not established. This verdict does not assess broader movement coverage or current applicability.