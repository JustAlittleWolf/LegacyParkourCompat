# Slipperiness sampling coverage through 1.19.4

## Evidence accepted for this change

This implementation is bounded by the exact-source snapshot commit
`5688e68528861c91031a021f484f80a90c887f2f`,
`workflows/source-campaign-2026-10-07/slipperiness-source-range-evidence-2026-10-08.md`,
and its independent source-only acceptance commit
`65b711d6a5145dbcc979d982de334e2a762f401c`,
`workflows/source-campaign-2026-10-07/reviews/slipperiness-source-range-review-2026-10-08.md`.
Both commits are in the local object database at
`D:/Javastuff/LegacyParkourCompat/.task-worktrees/review-mcpk-slipperiness-1-15-2-2026-10-08/.git`.

The accepted review verified the direct double sample
`BlockPos.containing(x, boundingBox.minY - 0.5000001, z)` at every ready
source endpoint from 1.15.2 through 1.19.4, along with the player travel path
to the friction consumer. At 1.20.1, the source uses the supporting-block
cache and `getOnPos(0.500001F)`. The snapshot does not establish exact patch
boundaries inside `(1.14.4, 1.15.2]` or `(1.19.4, 1.20.1]`.

The implementation-coverage discussion embedded in the snapshot was not part
of the source review verdict. The resolver mapping below is reconciled against
this repository's `ChangeResolver`, provider registrations, version groups,
and the shared `LivingEntityMixin` ground-friction hook.

## Registration and resolution

The implementation reuses the accepted 1.15.2 sampler without changing its
formula. Its normal annotated registration remains at `V1_15_2`. A second
registration binds the same hook implementation at `V1_19_4`, the last
supported profile in the source-confirmed direct-sampler family. Because
`ChangeResolver` chooses the closest registration whose emulated version is
not older than the selected version, the resulting coverage is:

| Selected profile | Resolved ground-friction sampler |
| --- | --- |
| `V1_14` | Existing `V1_14` `minY - 1.0` behavior |
| `V1_15`, `V1_15_2` | `V1_15_2` direct-double sampler |
| `V1_16`, `V1_16_2`, `V1_17`, `V1_17_1`, `V1_18`, `V1_18_2`, `V1_19`, `V1_19_4` | `V1_19_4` registration of the same direct-double sampler |
| `V1_20`, `V1_20_2`, later historical profiles | No applicable registration; native behavior |
| `CURRENT` | No movement changes, as defined by the resolver |

The reviewed source points establish the same sampler at 1.16.1, 1.16.2,
1.16.5, 1.17.1, 1.18.2, 1.19.2, 1.19.3 and 1.19.4. `ParkourVersion`
groups those ready releases into the listed selectable profiles. This does
not locate an unobserved cutover inside a group or either unresolved interval.

The shared hook dispatch still falls back to vanilla when no behavior resolves.
No mixin, movement loop, physics implementation, block state, or core resolver
was changed. No build, test, client, TAS, server, Gym, or Docker operation was
run for this implementation task.
