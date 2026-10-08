# Fix handoff: F-1 shallow water current cutoff

## Finding and immutable source review

- Finding snapshot: `6374ff904018e102afd8353864d3cbcbb1b99ddd`,
  `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-1-water-current-shallow-overlap.md`.
- Finding blob: `90c8a48282c036e6082c85f0be7a69e041ac0cb7`; raw SHA-256:
  `9d2c273a1c3b358eac7ebaa1e4cbe9e91a126648a90e89d8d6bdeb1953159cdd`.
- Independent source-only acceptance: commit
  `f222615162499441f959899600744813b1276614`,
  `workflows/source-campaign-2026-10-07/independent-review-water-current-12111-2612-2026-10-08.md`.
- Pair status remains partial. The review accepts this water-current witness only; it does not accept full-pair coverage, every fluid, implementation binding, runtime parity, or a first changed release.

## Current implementation reconciliation

At base `270e8c85d656a0d2fae0770799e207ed1557d9ba`, the existing
`FluidCurrentMinimum` change is registered at `V1_15_2` and returns `false`.
`EntityFluidCurrentMixin` uses it to alter the later `Vec3.length()` minimum-
impulse check. It does not intercept the earlier `Vec3.lengthSqr()` cutoff
in `EntityFluidInteraction.Tracker.applyCurrentTo()`. The existing registration
therefore does not cover F-1 for `V1_21_11`; the native weak-current cutoff
returns before the minimum-impulse path.

The accepted source record identifies the player-side difference at the
reachable shallow-water state:

- 1.21.11 `Entity.updateFluidHeightAndDoFluidPushing`, source SHA-256
  `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`,
  lines 3509–3572: shallow flow is overlap-scaled, then the player minimum-
  impulse logic remains reachable.
- 26.1.2 `EntityFluidInteraction.Tracker.applyCurrentTo`, source SHA-256
  `5264ff4f1fddebc3fa9d63ed2adbe2eaf617392ff946a867a6817a78b478ee62`,
  lines 153–193: the strict squared-current cutoff runs before averaging,
  water scaling, minimum impulse, and velocity addition.
- The configured build target is 26.2. Its ready `EntityFluidInteraction.java`
  has the same SHA-256 as 26.1.2, so the redirect target and cutoff body match
  the independently reviewed B source; the containing `Entity.java` SHA-256
  is `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- The review verifies the player tick route, `!abilities.flying` gates, the
  one-cell real water flow, the loaded-chunk and water-tag prerequisites, and
  the matching water strength `0.014`. Its concrete witness is a non-flying,
  unmounted player with a shallow nonzero water current and near-zero X/Z
  velocity. It proves a conditional delta-velocity difference, not a trajectory.

## Delta and version resolution

`ShallowWaterCurrentCutoffBehavior` is a separate hook because the early
`lengthSqr()` gate and the existing later minimum-impulse `length()` check are
different operations. The shared `EntityMixin` water-current call already
knows the fluid tag and entity. For a player whose selected profile resolves
the new behavior, it checks the water tracker's height and scopes the
downstream cutoff dispatch to that one shallow-water call. `EntityFluidCurrentMixin`
then bypasses only the squared-length predicate, returning positive infinity
for the predicate value while leaving the accumulated vector and every later
vanilla operation unchanged. The existing current-minimum hook still controls
the later `length()` check independently.

| Selected profile | Cutoff behavior from this change |
| --- | --- |
| `V1_21_11` and older | For player WATER calls with `0 < waterHeight < 0.4`, bypass the new weak-current cutoff. Other fluids and deeper water retain vanilla cutoff behavior. |
| `V26_1` (`26.1`, `26.1.1`, `26.1.2`) | No applicable `V1_21_11` change; retain native behavior, using the latest-patch source representative `26.1.2`. |
| `CURRENT` (26.2) | Resolver returns no historical changes; retain native behavior. |

`V1_21_11` is an exact singleton profile. The only later selectable profile
is `V26_1`, whose latest-patch representative is the reviewed 26.1.2 endpoint.
The finding's first changed release remains unknown in `(1.21.11, 26.1.2]`;
the registration is anchored to the exact older behavior being emulated and
does not claim where the newer cutoff was introduced. Ready source trees for
26.1.0 and 26.1.1 are absent. They are not needed to choose this profile
boundary because no change is registered within `V26_1`; this handoff makes no
patch-specific claim about those releases. A later task that needs to split
that group or locate the first changed patch must request their exact sources
serially from the source-preparation owner.

## Scope and validation

- Changed code: new shallow-water cutoff hook and `V1_21_11` registration;
  existing water dispatch and tracker cutoff bridges are extended.
- The existing boat-passenger water suppression runs first. Lava and other
  fluids do not receive this bypass. Non-player entities do not resolve the
  hook. Disabled emulation, `V26_1`, and `CURRENT` leave the vanilla predicate
  unchanged.
- No vector, scale, normalization, minimum impulse, or velocity expression is
  copied or reordered. This delta only allows the old shallow-water case to
  reach the unchanged downstream computation. Runtime behavior outside the
  accepted witness, mounted-player cases, other fluids, and the broader S4.6
  inventory remain open.
- No build, test, decompilation, client, TAS, server, Gym, Docker, or runtime
  work was run. The exclusive build owner and independent implementation
  reviewer are expected to continue after this committed handoff.
- Source-discovery feedback has not been sent to source-only owners.
