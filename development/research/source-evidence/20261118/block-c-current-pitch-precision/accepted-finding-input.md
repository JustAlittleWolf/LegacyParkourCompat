# Current-pitch precision: 1.13 through 1.16.5

## Accepted operation

For ordinary finite mouse pitch input applied to the current pitch value, the accepted historical calculation is a double-precision increment `pitchDelta = pitchInput * 0.15D`, added to the current pitch after promoting that stored float to double, narrowed once to float, then clamped to `[-90.0F, 90.0F]`. This finding covers only the current pitch value and the exact selected release groups listed below. It does not cover prior-tick pitch, yaw, mouse scheduling, movement parity, or non-finite input.

## Exact source witnesses

- Minecraft 1.13, Ornithe Feather, `net.minecraft.entity.Entity.updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)`: `Entity.java` lines 318–324. The current pitch uses `cursorDeltaY * 0.15` as a double, then `(float)(pitch + delta)` and a float clamp; the previous-pitch update is a distinct later operation and is outside this finding.
- Minecraft 1.14, Yarn, `net.minecraft.entity.Entity.changeLookDirection(double d, double e)`: `Entity.java` lines 313–319. The current-pitch argument is `e`; its double increment, float narrowing and clamp match the operation above.
- Minecraft 1.16.5, Mojmap, `net.minecraft.world.entity.Entity.turn(double d, double e)`: `Entity.java` lines 340–346. Current `xRot` uses the second double argument, adds its double increment before narrowing to float, and clamps as float.
- Minecraft 26.2, unobfuscated, `net.minecraft.world.entity.Entity.turn(double xo, double yo)`: `Entity.java` lines 489–497. The two double parameters are ordered yaw (`xo`) then pitch (`yo`); current pitch uses the second argument. Its present implementation narrows that pitch input before multiplying by `0.15F`, writes/clamps current pitch before writing yaw, then updates previous pitch separately. Source evidence binds the setter order and the accepted finite-input comparison; this is a source operation finding, not a claim about unrelated movement behavior.

## Accepted applicability

The source report `c-pitch-width-113-1165-applicability/report.md` is accepted with SHA-256 `f0d7b66fc093d56affd71b7ca0af34b102a5d3028646481abc5a7259894a0901`. Its exact source evidence is archived in these Git bundles/commits:

- `c-turn-precision-sign-normalized-evidence-20261118.bundle`, SHA-256 `950876e546956f69f15fbed2eeed4c6ae86fdbb42ccc09acec2ebe2e7f56096d`, archive commit `46480232f23906ce03ad862e1b5eccfac3fd65cd`.
- `c-turn-precision-pitch-yaw-evidence-20261118.bundle`, SHA-256 `94d34e101039d0add02b2b88f951335a1120a53bdfe6559ab11131021f331846`, archive commit `54d34113e2db3827eca267c57799edf18cb439d9`.
- Continuity archives: commit `8d2e74c77b82af37f14a4e44c8051d80093988b3` and commit `9ef25ce2047fdeb52ea9b93e07c2e3a407800948`.

The accepted resolver groups are: `V1_13` for 1.13; `V1_13_1` for 1.13.1–1.13.2; `V1_14`, `V1_14_1`, and `V1_14_2` for their corresponding release groups, with `V1_14_2` covering 1.14.2–1.14.4; `V1_15` for 1.15–1.15.1 and `V1_15_2`; `V1_16` for 1.16–1.16.1 and `V1_16_2` for 1.16.2–1.16.5. The next exact source boundary is 1.17. Exact alias coverage and exclusions are defined by the accepted report and archived comparisons.

## Limits

Only the current-pitch arithmetic is in scope. Prior pitch, yaw, callback timing, scheduling, non-finite inputs, and movement-loop behavior are separate questions. The modern comparison demonstrates a changed finite current-pitch result at the accepted boundary; this input does not assert an implementation design or a product integration location.

