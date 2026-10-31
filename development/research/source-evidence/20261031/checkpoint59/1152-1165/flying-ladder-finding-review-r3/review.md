# Independent source review: flying ladder descent

**Verdict: ACCEPT**

## Immutable inputs

- Draft: `.native-source/20261030/1152-1165-finish43/canonical-findings/1.15.2--1.16/sneaking-flight-ladder-descent.md`
- Draft SHA-256: `AFFF3A35560D85A83C62BBE02446E8E0BE1584EE2A14640B3E84FB2EAA486615`
- Boundary version-ID erratum: `.native-source/20261030/1152-1165-finish43/canonical-findings/1.15.2--1.16/ladder-boundary-version-id-erratum.md`
- Erratum SHA-256: `7A2EF22BDAEF47281A3945690454C7C2E96E2426C713EAFDA8F89343826C67A9`
- Erratum is bound to intermediate-boundary report SHA-256 `D74AF8B7F76D81556331524916DDDCA0959C7A9B49C86179485A74589DBE690B`.

## Review

The version-ID correction is valid: the canonical Mojmap release ID is `1.16`, and the local ready manifest identifies `versionId: 1.16` with mapping `mojmap`. The finding and erratum consistently use `1.16`; the original intermediate report's `1.16.0` wording is corrected by the bound erratum. The source-supported boundary is 1.15.2 → 1.16.

The exact source methods support the finding:

- In 1.15.2, `LivingEntity.isSuppressingSlidingDownLadder` returns the shift state (`LivingEntity.java:2686–2688`); `LocalPlayer` has no override. In 1.16, `LocalPlayer.java:347–349` adds `!abilities.flying && super.isSuppressingSlidingDownLadder()`; the inherited predicate still returns the shift state.
- The proposed block witness is accepted explicitly on both sides without relying on tag membership. 1.15.2 `onLadder` rejects spectators and its trapdoor fallback requires an open trapdoor above a ladder with matching facing (`LivingEntity.java:1226–1248`). 1.16 `onClimbable` rejects spectators and has the same explicit fallback (`LivingEntity.java:1361–1393`).
- Both `handleOnClimbable` methods clamp Y with `Math.max(y, -0.15)` before zeroing a negative result only when the feet block is not scaffolding, suppression is true, and the entity is a `Player` (1.15.2 `LivingEntity.java:1989–2003`; 1.16 `:2087–2103`). Both install the returned movement immediately before `move(MoverType.SELF, ...)` (1.15.2 `:1890–1891`; 1.16 `:2062–2063`).
- LocalPlayer's flying shift input and `Player.travel` flying branch are accurately cited. The latter saves Y before `super.travel`, then writes that saved pre-super Y velocity times `0.6` afterward (1.15.2 `Player.java:1448–1470`; 1.16 `:1380–1402`). That post-move velocity write does not undo a position displacement already made by the `SELF` move.

The source identity check confirms the exact `1.16` release ID/mapping and matching LocalPlayer source-manifest/file hashes. The checked 1.16.1–1.16.5 LocalPlayer sources retain the same flying guard; where source manifests/ready records are published, their LocalPlayer hashes match. This corroborates the erratum's checked-patch claim. The checked boundary therefore is not an inferred `1.16.0` alias or a guessed patch interval.

The stated witness and limitations are appropriately bounded: nonspectator local player, flying with shift held, negative Y, the explicit open-trapdoor-over-ladder state with matching facings, ordinary dry-land travel, no passenger or fall-flying branch, and no clipping. Under these conditions, old suppression changes clamped negative Y to zero while the new predicate leaves it at the clamped negative value, producing a downward position difference on that move. No runtime parity or map-prevalence claim is made.

The revised sentence correctly calls the restored value the **saved pre-super Y velocity**. I found no remaining evidence or wording issue that changes acceptance.
