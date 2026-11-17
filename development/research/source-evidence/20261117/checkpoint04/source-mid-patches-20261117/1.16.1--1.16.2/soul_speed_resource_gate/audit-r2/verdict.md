# Independent audit — 1.16.1 → 1.16.2 Soul Speed resource and witness

Verdict: **ACCEPT** for the bounded source/resource tag membership and static ordinary-state witness. This closes the packaged tag and stated witness gate for this candidate only; it does not freeze the pair, close all source dependencies, or establish runtime parity.

## Immutable inputs

- Report: `resource-gate-report-r2.md` — SHA-256 `a61b8c32cd07ed81906f7e406f29777a829966a2b1baf2059fcf3e80949c0a68`.
- Input reference: `input-reference.json` — SHA-256 `064916f9f13d72ee6381e235b2f1117e20ea7663c86707b257bfab00334cfc00`. Parsed as JSON; pair, namespace, endpoint count, allowlist and no-other-entry claim match the report.
- Earlier code-path draft: `ordinary_player/soul-speed-block-position.md` — SHA-256 `DE361025678422E62CF65B7633377364FB6FD673986F4B98BBA6E11F4571D5AB`; its bounded code path and open-gate language are covered by the independent source audit `ordinary_player/audit/verdict.md` — SHA-256 `5203F7819178EB5EE399CD8DE31DFD5482B82B5657A4277923B9790BD3FED57E`.

## Source identities and binding

Both sources are exact Mojmap publications. The source manifest and provenance files matched the identities in the input locator, and the ready markers identify the exact version, namespace, manifest and pinned client SHA-256:

| Release | Source manifest SHA-256 | Provenance SHA-256 | Ready marker client SHA-256 |
|---|---|---|---|
| 1.16.1 | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` | `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef` |
| 1.16.2 | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c` | `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d` |

Cited Java-file hashes match the exact endpoint manifests and disk: `BlockTags.java` (`7f506e2386851a128c12a449683b6f4fe1d0007fe2e74524116df73c8db30a7d` / `99f23e86191ba49033800be965341c4408b002404e9f403e3f4d42f962f1e88b`), `BlockTagsProvider.java` (`bba0bac845a3fe3f76b3b78990421aa0ebf2d6a77982df395875763c9a34df7b` / `9f084139857f7fd2c78928d89596d8b224c5959fc374191d755e17dadca4c239`), `LivingEntity.java` (`b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` / `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`), and `Entity.java` (`27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` / `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`). For the witness geometry/speed, `Blocks.java` is `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9` / `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`; `SoulSandBlock.java` is byte-identical with manifest SHA-256 `da92572788c9416b511c003e50688d802e8248ccdb3e2d5eb155aa482549b095`.

## Independent artifact and entry verification

I independently fetched each exact version metadata object from the official `piston-meta.mojang.com` URL in the input reference. The metadata SHA-1 matched its object ID in the URL, and its `downloads.client.url` and `downloads.client.sha1` matched the specified client object URL and SHA-1. I fetched each client jar from `piston-data.mojang.com`, checked its SHA-1 against that object ID and its SHA-256 against the ready marker above, then opened only `data/minecraft/tags/blocks/soul_speed_blocks.json`.

| Release | Metadata SHA-1 / URL object ID | Client SHA-1 / URL object ID | Client SHA-256 | Allowlisted entry |
|---|---|---|---|---|
| 1.16.1 | `54fa3af57d041d2771e66d390197b2c0288e697c` | `c9abbe8ee4fa490751ca70635340b7cf00db83ff` | `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef` | 94 bytes, SHA-256 `c86fbd7bcfa2b94c881f0ba9980a694b28150a5b0b628aec0cfedc552f68994f` |
| 1.16.2 | `998d9ef5770d05c20d760dc16cf85151f35009f2` | `653e97a2d1d76f87653f02242d243cdee48a5144` | `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d` | 94 bytes, SHA-256 `c86fbd7bcfa2b94c881f0ba9980a694b28150a5b0b628aec0cfedc552f68994f` |

Both independently extracted entries parse as `{"replace": false, "values": ["minecraft:soul_sand", "minecraft:soul_soil"]}` and match the packet payload files byte-for-byte by SHA-256 and length. Temporary metadata, client jars and audit extraction files were removed after verification. No other archive entry was extracted or read. The ready markers record artifact-manifest SHA-256 values `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` and `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108`; the manifest payload files were not present at the canonical ready paths in the current campaign tree, so I did not claim to inspect their contents. Direct release-metadata-to-client-object binding and ready-marker client pins were verified instead.

## Source consequence and witness

`BlockTags.java` binds the key `soul_speed_blocks`, and the exact default `BlockTagsProvider` source in both releases includes Soul Sand and Soul Soil. The independently verified packaged tag entries confirm those are the actual default members of the vanilla client resource.

For the stated legal state, put Soul Sand at y=63, leave y=64 air, and consider a player in a jump at position y/minY 64.3. Old `getOnPos()` floors `64.3 - 0.2F` to y=64; its fallback selects the lower block only for a fence, wall or fence gate, so it stays on air. The new movement-affecting position floors `64.3 - 0.5000001` to y=63. Soul Sand’s collision shape reaches y=63.875, leaving 0.425 blocks of clearance at minY 64.3. `Blocks.java` sets Soul Sand’s `speedFactor` to 0.4F in both versions. With a positive Soul Speed level, the old tag check is false and the superclass factor resolves to Soul Sand’s 0.4F base factor; the new tag check is true and the override returns 1.0F. This changes the horizontal multiplier applied in `Entity.move` for this state. A positive enchantment and jump state are ordinary legal player state; the witness does not assert a generated map contains it.

Verdict is limited to default packaged vanilla membership and this static source-derived witness. Arbitrary datapack overrides, server acceptance, runtime parity, other movement dependencies and exact-pair completion remain outside this acceptance.

