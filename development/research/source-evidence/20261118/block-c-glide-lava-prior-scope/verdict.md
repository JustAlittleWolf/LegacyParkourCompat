# Independent audit: earlier glide cap versus ordinary lava routing

Status: **SUPPORTED within the bounded source-state claim; no trajectory claim.** Every supplied stable release label from 1.19.3 through 1.21.1 has the older cap inside the fall-flying `travel` arm, after ordinary lava routing. A qualifying ordinary lava route therefore moves before the old cap could be called, and prevents that cap call during the same `travel` because the branches are mutually exclusive. The compatible active-glide + ordinary-lava + foot-cell climbable witness is a conditional predicate state, not a placement or trajectory claim.

## Raw evidence bindings

Audit-input digest (SHA-256 over the four exact identity lines below, UTF-8 with a final LF): `d5d701d4a0d2fafe7f0f4368ef2bc2c5c5522bcf14ed786bfc5eccdfe5c3ce74`.

- Earlier scope comparison payload, raw bytes: `58204e94fb4c697bcfe0b7de87e965c7f7b0bd1a22c38ff0a08c0f176b0796a7`.
- Earlier scope supplement payload, raw bytes: `932c220420a92aef908567b156832162e7bf20fb1882aa07ccedf04e945ff704`. The supplement embeds the base comparison digest above.
- Accepted wet 1.21.1 → 1.21.2 comparison payload, raw bytes: `3e1b52e3f5ad10c6008a417133b7e9d1e855d2e34770d23ea8b00badefbaa537`.
- Separate accepted wet audit verdict payload, raw bytes: `e9382dee8b692dc4cea9ad29f0538a604d68125d794d189cd56ed21def24484b` (`SUPPORTED, conditionally admissible`). The verdict supports the later pre-travel cap and the lava/climbable source-state witness, without incoming-trajectory inference.

These are distinct payload identities. The outer comparison files are bound by their raw SHA-256; release-source identity is checked separately below, including each local provenance/ready identity and the embedded constituent hashes where available.

Audit identity lines (UTF-8; four lines, final LF):
~~~text
priorScope.comparison.md=58204e94fb4c697bcfe0b7de87e965c7f7b0bd1a22c38ff0a08c0f176b0796a7
priorScope.comparison-r2.md=932c220420a92aef908567b156832162e7bf20fb1882aa07ccedf04e945ff704
acceptedWet.comparison.md=3e1b52e3f5ad10c6008a417133b7e9d1e855d2e34770d23ea8b00badefbaa537
acceptedWet.verdict.md=e9382dee8b692dc4cea9ad29f0538a604d68125d794d189cd56ed21def24484b
~~~

## Exact stable-release roster and source identity

Canonical read-only sources: `decompiled_minecraft/<release>/mojmap/net/minecraft/world/entity/{LivingEntity,Entity}.java`.

| Exact release label | Identity class | Local source/provenance identity | LivingEntity SHA-256 | Entity SHA-256 |
|---|---|---|---|---|
| 1.19.3 | ready Mojmap | ready marker and source manifest: `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` |
| 1.19.4 | ready Mojmap | ready marker and source manifest: `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` |
| 1.20 | provenance-pinned Mojmap snapshot; no ready marker | provenance JSON: `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| 1.20.1 | ready Mojmap | ready marker and source manifest: `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| 1.20.2 | ready Mojmap | ready marker and source manifest: `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486acf9f96ff69828` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` |
| 1.20.3 | provenance-pinned Mojmap snapshot; no ready marker | provenance JSON: `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| 1.20.4 | ready Mojmap | ready marker and source manifest: `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| 1.20.5 | ready Mojmap | ready marker and source manifest: `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| 1.20.6 | ready Mojmap | ready marker and source manifest: `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| 1.21 | provenance-pinned Mojmap snapshot; no ready marker | provenance JSON: `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| 1.21.1 | ready Mojmap | ready marker and source manifest: `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |

For the eight ready entries, the `mojmap.ready.json` identity and `mojmap.sources.sha256` entries were checked against the exact version/mapping and the actual two file hashes; each reported source-manifest SHA-256 was recomputed. For 1.20, 1.20.3 and 1.21, the local `.decompile-provenance.json` raw hash was checked and its embedded source entries matched the actual `LivingEntity.java` and `Entity.java` hashes. Their artifact/mapping-input pins remain as stated in the supplied supplement; no readiness marker is inferred for them.

The byte-identical source pairs 1.20/1.20.1, 1.20.3/1.20.4, 1.20.5/1.20.6, and 1.21/1.21.1 are exact identities of these two files only. They do not authorize or establish mod profile aliases. The 1.21.2/1.21.3 exact source alias and 1.21.4 continuation belong to the separately accepted narrow 1.21 campaign evidence above. Neither should be folded into an older-version cutover claim.

## Source audit

Across all eleven labels, the searched operation is present in each full `LivingEntity.java`: the ordinary lava `else if` branch precedes the `isFallFlying()` branch; `move(MoverType.SELF, ...)` occurs in the lava branch before its fluid adjustment; `checkSlowFallDistance()` occurs at the start of the later fall-flying branch. The relevant call pairs are: 1.19.3 lines 2097/2118; 1.19.4 2028/2049; 1.20 and 1.20.1 2048/2069; 1.20.2 2082/2103; 1.20.3 and 1.20.4 2079/2100; 1.20.5 and 1.20.6 2148/2169; 1.21 and 1.21.1 2131/2152. The cap operation in each `Entity.java` remains `getDeltaMovement().y() > -0.5 && fallDistance > 1.0F`, followed by `fallDistance = 1.0F`; this is a same-travel call-order result, not a prediction of later fall-distance outcomes.

The compatible climbable predicate remains separate from fluid route selection: in 1.21.1 `LivingEntity.onClimbable()` reads `blockPosition()` and that position's block state (1517–1528); ordinary lava status comes from positive tracked lava-fluid height (1.21.1 `Entity.java:1346–1348`), updated by the fluid scan (`1244–1247`). The lava `travel` arm contains no `onClimbable()` gate; the climbable bounce is in the preceding water arm and gated on horizontal collision. Thus a foot-cell climbable predicate and body-AABB lava contact can both be true at source level, while ordinary lava routing wins in this active-local glide state. The source does not prove a specific map arrangement, incoming movement, or trajectory that reaches the overlapping predicates.

The earlier `travel` cap arm is shown at 1.19.3 `LivingEntity.java:2117–2119` and at 1.21.1 `2131–2153`. Local-client route guards and shared active glide bit are as recorded in the supplied evidence; this audit's bounded question is precedence after the predicates already hold. No continuous exact-ready status is claimed for provenance-only snapshots beyond the exact stable labels and source identities listed.

## Separation of claims

- **Earlier scope:** source behavior is verified for each stable release label listed above; no internal patch cutover or mod profile alias is established by the 1.19.3–1.21.1 roster. Identical source-file hashes are only file-level alias facts.
- **Narrow 1.21 operation:** the separate accepted 1.21.1 → 1.21.2 audit supports the changed pre-travel cap conditionally; 1.21.2 → 1.21.3 file identity and 1.21.4 behavioral continuation remain separate claims. Lava is the conditional positive residual witness; water resets fall distance and is not.
- **Non-claim:** there is no trajectory, placement, map-reachability, or runtime parity result here.

Terminal: **COMPLETED**. No product sources, wiki material, tests, builds, launches, or source generation were used.