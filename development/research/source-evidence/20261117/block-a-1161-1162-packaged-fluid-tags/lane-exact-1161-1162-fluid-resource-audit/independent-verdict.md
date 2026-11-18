# Independent audit verdict: 1.16.1–1.16.2 fluid tags

**Verdict: ACCEPTED for the bounded packaged-resource claim; no difference.**

Frozen independent roster: `frozen-roster.md`, SHA-256 `443acea2db313a8cb912fa90b0e1ae24466cf1ce51ba9fc47b98a2c506dd58d3`. It was frozen before reading the author comparison.

## Identity and evidence

- Authorized `resource-manifest.json` SHA-256: `d43b960acb84cee548252b17455df46f273d0f9f06cc8c0718843d6a1ed99dcc`.
- Local published `mojmap.ready.json`, `mojmap.sources.sha256` and `artifacts.sha256` all match their manifest SHA-256 pins for both exact releases. Ready records say status `ready`, version IDs `1.16.1`/`1.16.2`, mapping `mojmap`, and bind the same source/artifact pins and client digest as the manifest.
- Local official metadata JSON bytes match the manifest metadata SHA-1 identifiers and SHA-256 pins. Metadata IDs, exact version IDs, client download URL, client SHA-1 and byte size match the corresponding manifest values.
- Local client archives match their exact manifest SHA-256 and metadata client sizes: 1.16.1 `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef` (17,492,432 bytes); 1.16.2 `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d` (17,505,439 bytes). Their SHA-1s equal their metadata `downloads.client.sha1` fields.
- I opened exactly the two named `data/minecraft/tags/fluids/{water,lava}.json` entries in each pinned client archive. All four entry byte lengths and raw SHA-256 values equal the input manifest. The parsed JSON has `replace: false`, the listed ordered values and no `values` include objects.

## Roster verdict

The exact packaged water entries are identical between versions: `minecraft:water`, `minecraft:flowing_water`. The exact packaged lava entries are identical: `minecraft:lava`, `minecraft:flowing_lava`. Their raw bytes and hashes match across versions. There are no includes. No membership/order/replacement change exists in this four-entry roster.

## Author comparison check

The author file `comparison.md` has SHA-256 `30ee98fca1b981758f5b8886a8026b2fdb4d4603cd34915a69174c444e94c8f6`, matching the assigned immutable identity. Its four entry paths, byte lengths, raw hashes, `replace` state, ordered values, no-includes claim and no-difference verdict all agree with my frozen direct archive inspection. Its input-identity summary is supported by the verified ready, artifact, metadata and client archive pins above.

## Terminal scope

This verdict accepts only the bounded packaged vanilla defaults for the water and lava fluid tags in client releases 1.16.1 and 1.16.2. It does not establish datapack merge/priority/reload behavior, runtime-effective membership, fluid travel behavior or general movement parity. No archives were executed, no files were downloaded, and no game/build/tests were run.
