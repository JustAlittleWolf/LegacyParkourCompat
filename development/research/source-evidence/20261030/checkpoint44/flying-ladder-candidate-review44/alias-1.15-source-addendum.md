# Bounded source addendum: 1.15 profile aliases

**Disposition: ACCEPT the shift-only predicate for the V1_15 profile alias (1.15 and 1.15.1), as a bounded extension to the accepted 1.15.2→1.16 finding.** This does not extend the claim to profiles before 1.15.

## Exact source identity

Read-only source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15\mojmap` and `...\1.15.1\mojmap`, Mojmap.

| Release | `mojmap.ready.json` SHA-256 | `mojmap.provenance.json` SHA-256 | `mojmap.sources.sha256` SHA-256 | source-file hashes (`LivingEntity.java`; `LocalPlayer.java`) |
|---|---|---|---|---|
| 1.15 | `C6B66B63BAFAF2096AD7606111F1D0FAE1F2178D624D6A8C9DED5AD3AC1AAA31` | `DE4A538EBBAF82D1615683B08ECAE3CD76553345F3079436764EB095BE343949` | `251A3019CCC3FD067F66365F3545B69A983383A28B19A1038DEBF1791BE29340` | `139C3866AD2C6A1AD73496F22FF1A4B5259998442092FFBAFB8664428E28BF79`; `7227DA5233E3C72CED008187B92A6A86842F84778EAA9F40FA1EB117286AF823` |
| 1.15.1 | `AFBB16C3CB832FFEE8E4763BF9933D9635ECD811C9FBD7166554B78E59788504` | `6A7AF1F5D65D7AF165C2AB9A90C1FAE20169B38E70C8E92DBC5F59D94EFE9461` | `C8FDCE2F9432B72FC13C19A48F9AB3BE203CAC41A617139E81BAC3552245912D` | `139C3866AD2C6A1AD73496F22FF1A4B5259998442092FFBAFB8664428E28BF79`; `7227DA5233E3C72CED008187B92A6A86842F84778EAA9F40FA1EB117286AF823` |

The ready records identify exact release IDs 1.15 and 1.15.1, Mojmap, and each source-manifest digest. I verified each ready record's manifest digest against the actual manifest and each listed source-file digest against the actual source file. The separate releases have identical hashes for both relevant source files, while retaining distinct release metadata/client identities.

## Behavior and scope

In both releases, `LivingEntity.isSuppressingSlidingDownLadder()` (`LivingEntity.java:2686–2688`) returns `this.isShiftKeyDown()`. `LocalPlayer` declares no override of that predicate. Its `isShiftKeyDown()` (`LocalPlayer.java:591–593`) returns `this.input != null && this.input.shiftKeyDown`. The movement call remains in `LivingEntity.handleOnClimbable()` (`:1989–2000`): it clamps Y to at least `-0.15`, and zeroes negative Y only when feet are not scaffolding, the predicate is true, and the entity is a Player. The adjusted movement is then applied before the `MoverType.SELF` move (`:1890–1891`).

This supports applying the already accepted old shift-only predicate to `ParkourVersion.V1_15`, which aliases 1.15 and 1.15.1, as well as the previously accepted `V1_15_2` behavior. It specifically corrects the original candidate review’s observation that a native-return fence at V1_15 is wrong for these profiles. With the existing smallest-`emulates`-greater-than-or-equal resolver, moving a native-return fence to V1_14_2 leaves V1_15 and V1_15_2 to resolve the shift-only implementation. The V1_14_2 fence is only a bounded implementation barrier preserving earlier profile behavior; this addendum makes no claim that native behavior equals historical behavior at 1.14.2 or any earlier release, and does not accept coverage below V1_15.

The source arithmetic and hook-boundary review from the original report remain unchanged. This is static exact-source review only; it does not establish runtime parity.
