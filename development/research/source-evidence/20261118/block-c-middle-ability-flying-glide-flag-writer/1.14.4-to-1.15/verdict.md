# Independent audit: 1.14.4 → 1.15 creative-flight toggle during live Elytra glide

## Verdict

**ACCEPT — the frozen report’s no-difference conclusion is supported for the assigned constrained tick.** The audit covers only a local, dry, airborne, nonpassenger player with a live shared flag 7, enabled Elytra in the chest slot, `abilities.mayfly == true`, and a lawful transition of `abilities.flying` from false to true during `LocalPlayer.aiStep`. It does not accept whole-pair coverage or establish an intermediate-version cutover.

## Independent trace

- `LocalPlayer.aiStep`: 1.14.4 lines 690–704; 1.15 lines 695–713. Both allow a double-jump flight toggle under `mayfly`, with the same trigger timer/reset and no-swimming gate. The 1.15 `bl8` latch is an additional local variable; the change to the fresh-glide request below does not send a start request in this witness. A newly true `abilities.flying` blocks the 1.14 request; 1.15 also blocks it and requires `tryToStartFallFlying()`.
- The `super.aiStep()` call occurs after this toggle in both versions (1.14.4 line 772; 1.15 line 778). The post-super flight disable is gated by `onGround` (lines 773–775 / 779–781), so it cannot undo the newly enabled ability in the specified airborne state.
- `Player.travel(Vec3)`: 1.14.4 lines 1380–1408; 1.15 lines 1456–1486. Both take the `abilities.flying && !isPassenger()` branch, save incoming Y and flying speed, call `super.travel(vec3)` while shared flag 7 remains true, restore horizontal components and set Y to the saved value times `0.6`, restore flying speed, write `fallDistance = 0.0F`, then directly clear shared flag 7. Movement statistics follow. The coordinate getters introduced in the newer source do not alter this sequence.
- `LivingEntity.travel(Vec3)`: 1.14.4 lines 1782–1835; 1.15 lines 1831–1884. Under the witness, `isFallFlying()` remains true during the superclass call, and the glide math reaches `move(SELF, deltaMovement)` before `Player.travel` clears flag 7. The shared-flag clear inside this method additionally requires on-ground and server-side; it is false for the assigned airborne client tick.
- `fallDistance` is a distinct state write: live glide travel may set it to `1.0F` when the pre-move Y velocity is greater than `-0.5`; the enclosing creative-flight branch then independently resets it to `0.0F` after `super.travel`. The ordering supports the report’s separate fall-distance treatment; it is not evidence of a different flag-7 result.

## Evidence identity and binding

Audited the canonical read-only Mojmap sources in `decompiled_minecraft/1.14.4/mojmap/` and `decompiled_minecraft/1.15/mojmap/`. Both ready markers report the exact release and `mapping: mojmap`; their source-manifest digests match the marker fields. The three relied source files per side match their source-manifest entries and direct SHA-256 checks:

| Relative file | 1.14.4 SHA-256 | 1.15 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |

Source manifest SHA-256: 1.14.4 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; 1.15 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`.

Frozen claim reviewed: `c-mid-1144-115-ability-flight-glide-clear-source/report.md`, SHA-256 `34f83783930ea7268d3cbf0cb432d4440fdb58e07ae4294ab5b2f1a6bf866c51`.