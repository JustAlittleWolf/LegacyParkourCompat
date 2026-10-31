# Final canonical findings review — 1.15.2 → 1.16 boundary

## Bound revisions

| Artifact | SHA-256 |
|---|---|
| `canonical-findings/1.15.2--1.16/shift-clears-double-tap-sprint-timer.md` | `6e73af4f6fd7d6664c8d7afabe59df03f91eac978807c53cc5db382179526651` |
| `canonical-findings/1.15.2--1.16/ability-flight-water-shift-descent.md` | `9d2a419abb6e684040406f21801146ee6b4dd18e9fd66b03c9bcc0d3bb6a9a69` |
| `canonical-findings/1.15.2--1.16/ability-flight-skips-ground-jump.md` | `e12d5c415f263c931df3f2d949bb40f80d02813e011be1aac56ce29cac9935c7` |
| `localplayer-candidate-boundaries/report.md` | `061f29239fc24920c635abf26b98e0daf56f2228430c2a44519e6bfb00d3d07f` |
| Accepted endpoint candidate report | `a0ad62636b1995748e8633a926b54625cae0310531519472362dd8510c919db1` |
| Accepted endpoint review addendum | `51b9024bed4dc2d802640a12fd472b3160cb88455e7a06844985dc07d3020353` |

The three finding hashes and boundary-report hash were recomputed and match the requested revisions. I compared each finding with the accepted candidate claims and the exact boundary report. The boundary report says each operation is present in release 1.16 and persists through 1.16.5, so the findings' `1.15.2 → 1.16` label is supported as the observed endpoint interval. It does not establish the exact introduction point before 1.16's release; the findings do not claim a finer pre-release cutover.

## Boundary source identity check

The boundary report's 1.16.3 provenance file recomputes to SHA-256 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`, and its 1.16.4 provenance file recomputes to `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`. Both embed the same `lpc-decompile-v1` pipeline and Mojmap mapping namespace. For each release, the embedded metadata, client, and mappings input hashes recompute to the three values listed in the boundary report.

For both 1.16.3 and 1.16.4, each of `LocalPlayer.java`, `LivingEntity.java`, and `Player.java` matches that release's embedded `sources` map. All six class hashes are byte-identical to the corresponding 1.16.2 and 1.16.5 source files. Thus the audited gate bodies and references are supported by each release's own embedded source identity, including .3 and .4.

## Per-finding verdicts

### `shift-clears-double-tap-sprint-timer.md` — ACCEPT

No material drift from the accepted sprint candidate. The 1.15.2 and 1.16 source paths, `aiStep` ranges, decrement/item-use ordering, sampled shift clear, and later sprint-trigger consequence are accurate. `LocalPlayer.aiStep:688–690` in 1.16 places the shift clear after item-use handling and before the sprint-start branch; that branch excludes the shift-held tick. The timing limitation is preserved: at the beginning of the shift-held tick the timer must exceed one, so the old decrement leaves a positive value. The boundary report confirms the same shift clear in 1.16.1 and 1.16.2–1.16.5, and its .3/.4 classes validate as described above.

Boundary qualification: “1.15.2 → 1.16” is accepted as the compared interval / latest-known endpoint. It does not prove the precise earlier introduction point within the interval.

### `ability-flight-water-shift-descent.md` — ACCEPT

No material drift from the accepted water-descent candidate. The 1.15.2 call and unchanged `goDownInWater` addition are accurately referenced. In 1.16, the LocalPlayer call adds the `isAffectedByFluids()` condition, and Player's override returns `!abilities.flying`; the boundary report confirms this gate persists through 1.16.5. This remains a direct local shift-descent response, separate from water-current handling.

The revised arithmetic is source-faithful: `Player.travel` captures incoming Y at 1.16 `Player.java:1396` and restores `i * 0.6` at `:1401`, so the direct source delta scales as `0.6 * (-0.04F)` under equal sampled state and the same flying branch. Replacing a decimal equality with the source expression removes any float/double representation ambiguity. The finding correctly leaves position displacement conditional on downstream travel and collision.

### `ability-flight-skips-ground-jump.md` — ACCEPT

No material drift from the accepted ground-jump candidate; the revised call order is exact. 1.15.2 `LocalPlayer.serverAiStep` copies sampled jump input into `this.jumping` at lines 607–616; in 1.16 the corresponding operation is at lines 634–640. `LivingEntity.aiStep` invokes virtual `serverAiStep` before jump selection in both endpoints. In 1.16 the selector guard includes `isAffectedByFluids()`, whose Player override is false during ability-flight. For clear air, ground, and zero delay at the selector, old source can apply the impulse while new source skips the selection and resets the delay. `Player.travel` then retains 60% of the incoming Y impulse if the player remains ability-flying through the current travel; the stated post-travel flight shutdown by LocalPlayer occurs later and does not undo that current movement. The boundary report confirms the same guard through 1.16.5, including its validated .3/.4 source bodies.

## Review boundary

These verdicts bind only the three immutable findings above to the accepted endpoint candidates and the cited boundary report. They establish the observed interval, not the exact pre-1.16 introduction patch, complete pair coverage, implementation, or runtime parity. No product implementation, tests, build, or runtime were examined.
