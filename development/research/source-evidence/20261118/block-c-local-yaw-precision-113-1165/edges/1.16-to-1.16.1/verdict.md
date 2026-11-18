# Independent yaw precision audit: 1.16 → 1.16.1

## Frozen verdict

**No source difference in the assigned bounded operation.** The ordinary direct yaw update and its first contribution to dry ground/air relative acceleration are identical in the exact local Mojmap source for 1.16 and 1.16.1. This closes only this yaw slice; it is not a full-pair movement coverage or runtime-parity verdict.

## Compared source path

All paths below are relative to `decompiled_minecraft/<version>/mojmap/` and were read directly from the exact local trees.

- `net/minecraft/client/MouseHandler.java:175–243` (`onMove`, `turnPlayer`): while mouse is grabbed and the window active, movement deltas accumulate as `double`; sensitivity and cubic scale are `double`; smooth and unsmoothed branches produce `double j/k`; the ordinary call is `player.turn(j, k * n)`. No finite-value guard precedes the call. The compared whole-file SHA-256 is identical in both versions: `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949`.
- `net/minecraft/world/entity/Entity.java:332–344` (`turn`): parameters `d/e` and scaled `f = e * 0.15`, `g = d * 0.15` are doubles. Current yaw is assigned first as `(float)(this.yRot + g)`; after the pitch clamp, previous yaw is independently assigned `(float)(this.yRotO + g)`. Yaw is neither clamped nor checked for NaN/infinity; the pitch clamps do not apply to yaw. This is byte-for-byte the same whole source file in both versions, SHA-256 `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`.
- `net/minecraft/world/entity/player/Player.java:1380–1410` (`travel`) sends the ordinary non-swimming, non-flying player path to `super.travel(vec3)`. The identical whole-file SHA-256 is `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` in both versions.
- `net/minecraft/world/entity/LivingEntity.java:1912–2040` (`travel`) takes the normal dry branch after water, lava and fall-flying branches, computes ground/air friction, then calls `handleRelativeFrictionAndCalculateMovement`; `:2060–2069` calls `moveRelative(getFrictionInfluencedSpeed(f), vec3)` before movement. Its whole-file SHA-256 is the same in both versions: `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`.
- `net/minecraft/world/entity/Entity.java:1073–1088` (`moveRelative`, `getInputVector`) is the first stored-yaw use on that path: it passes **current** `this.yRot` (not `yRotO`) to `getInputVector`. Inputs with `lengthSqr() < 1.0E-7` return `Vec3.ZERO` before yaw is read. Otherwise, after the normalization choice, the scaled `Vec3` is rotated using float `sin/cos` of `g * (float)(Math.PI / 180.0)`; the returned horizontal components are double vector components multiplied by those float trig values. `moveRelative` then adds that vector to the existing delta movement. The source expression and file hash above are identical in both releases.

The direct turn update can therefore preserve a non-finite yaw (or overflow a finite double sum when narrowed to float); no direct-turn finite guard intervenes. In the ordinary dry path, any resulting current stored yaw reaches the float angle/trig calculation on the first relative-input acceleration. `yRotO` is maintained for previous/interpolated rotation and does not feed this acceleration call.

## Local source and provenance integrity

For each release, the ready marker’s source-manifest SHA-256 matches the actual `mojmap.sources.sha256`; its artifact-manifest SHA-256 also matches the actual `artifacts.sha256`. The four source-file hashes above match their entries in each source manifest. The manifests report the same listed file hashes for these compared classes on both sides.

| Release | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Raw client SHA-256 (manifest entry) | Mojmap mappings SHA-256 (manifest entry) | Mapped client SHA-256 (manifest entry) |
|---|---|---|---|---|---|---|
| 1.16 | `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2` | `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71` | `53d46abe149aadd367be8789207c53a7cee46ba8382e5bfd60247eb22984119d` | `3213f0827fa9215f41e74ca1dedaf104738e9c70ea1635769fb90f3996e4bf6c` |
| 1.16.1 | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` | `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef` | `6cc374f819bca44d940f8912044b07bdac3e240a02e37117ae1d4ad63c21409b` | `f92d3ad192db28f84522436cc12bb92498ef3b8090f88e906d56d1fb8744490d` |

The 1.16 provenance document SHA-256 is `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9`, and the ready marker binds that digest. It records exact release `1.16`, Mojmap, the raw-client/mapping/mapped-client hashes above, and a successful exact-version decompile.

The 1.16.1 provenance document SHA-256 is `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`. Its own fields identify `versionId: 1.16.1`, `namespace: mojmap`, and a successful requested batch `1.16.1,1.16.2` with `--mappings=mojmap`; its artifact manifest lists the hashes above. **Integrity caveat:** unlike 1.16, the 1.16.1 ready marker has no `provenanceSha256` field, so the ready marker does not bind that provenance document by digest. The ready marker does bind the source and artifact manifests, and those digests plus the selected source-file entries were independently checked against the local bytes.

## Audit scope

I inspected only the assigned yaw direct-input/update and its ordinary dry relative-acceleration path in these two local exact-version source trees. No implementation, wiki material, product history, tests or runtime behavior was used.

## Phase-two reconciliation

The author’s frozen source report was verified at `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-yaw-precision-116-1161-source\report.md`, SHA-256 `1537f37e3439227bc68441c1c5a9c4ceec8f742bc2b877d221cd3b2997cea9c3`. Its identity matches the supplied frozen digest; the report was not modified.

I independently checked the two additional source slices it reports. In `Entity.load` (`Entity.java:1394–1427`), both versions read rotation floats into current yaw/pitch, copy those current values into previous yaw/pitch before validation, throw for nonfinite position coordinates, then gate position reapplication and `setRot` on finite current yaw and pitch. No separate previous-yaw finite test occurs. In `LivingEntity.jumpFromGround` (`LivingEntity.java:1880–1894`), both versions set vertical movement, then under the sprint gate compute `float g = this.yRot * (float) (Math.PI / 180.0)` and add `-Mth.sin(g) * 0.2F` / `Mth.cos(g) * 0.2F` to horizontal movement. These source files have matching whole-file hashes across the pair, so these added slices agree with the report and show no 1.16→1.16.1 yaw difference.

This confirms the frozen no-difference result for the bounded yaw slices. The author report excludes mouse/input-device plumbing; the prior audit separately checked the ordinary double-valued mouse path and found the same source in both versions. The previously recorded 1.16.1 ready-marker omission of a `provenanceSha256` field remains an evidence-binding caveat, not a source-content disagreement. Pair-wide coverage and runtime parity remain outside this audit.