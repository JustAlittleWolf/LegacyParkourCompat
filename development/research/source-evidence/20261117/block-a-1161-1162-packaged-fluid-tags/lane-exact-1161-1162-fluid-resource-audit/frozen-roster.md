# Frozen packaged fluid-tag roster and independent verdict

Input manifest SHA-256: `d43b960acb84cee548252b17455df46f273d0f9f06cc8c0718843d6a1ed99dcc`.

## Artifact identity gates

| Version | Local ready manifest SHA-256 | Artifact manifest SHA-256 | Mojang version metadata SHA-1 / SHA-256 | Client SHA-1 (metadata) | Client SHA-256 | ZIP bytes |
|---|---|---|---|---|---|---:|
| 1.16.1 | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` | `54fa3af57d041d2771e66d390197b2c0288e697c` / `9688ec094a807c45513733388f7c8426d80a58cee96ed86be5380cb6a45f4a73` | `c9abbe8ee4fa490751ca70635340b7cf00db83ff` | `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef` | 17492432 |
| 1.16.2 | `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214` | `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108` | `998d9ef5770d05c20d760dc16cf85151f35009f2` / `f696986f4c07cd12dede183c86266d5cd34def6846eb0f014e1a84c2a76688d4` | `653e97a2d1d76f87653f02242d243cdee48a5144` | `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d` | 17505439 |

The local ready manifests identify the requested exact versions and `mojmap`; each ready manifest binds the listed source and artifact manifest digests and client SHA-256. The artifact-manifest client rows match. Stored exact-version metadata bytes match both their Mojang metadata SHA-1 identifiers and manifest SHA-256 pins. The metadata `downloads.client` SHA-1/URL/size fields match the archive SHA-1/URL/size. Local client archives match the listed exact SHA-256 and byte length.

## Frozen roster

All four entries below were enumerated and inspected directly in the exact pinned client ZIPs before opening the author comparison file. Raw extracted entry byte lengths and SHA-256 values match the input manifest.

| Version | Exact ZIP entry | Raw bytes | Raw SHA-256 | `replace` | Values in order | Includes |
|---|---|---:|---|---|---|---|
| 1.16.1 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | false | `minecraft:water`, `minecraft:flowing_water` | none |
| 1.16.1 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | false | `minecraft:lava`, `minecraft:flowing_lava` | none |
| 1.16.2 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | false | `minecraft:water`, `minecraft:flowing_water` | none |
| 1.16.2 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | false | `minecraft:lava`, `minecraft:flowing_lava` | none |

**Independent membership verdict:** The packaged default `water` roster is identical across 1.16.1 and 1.16.2; the packaged default `lava` roster is identical across both versions. No entry was added, removed, reordered, replaced or delegated to an included tag. This is a no-difference result for the four enumerated source resources only.

## Scope boundary

This establishes only the two packaged vanilla fluid-tag JSON defaults in each exact client archive. It says nothing about data-pack merging, priority, reload/runtime effective membership, fluid traversal/flow, player fluid travel or broader movement parity. No game or runtime verification was performed.
