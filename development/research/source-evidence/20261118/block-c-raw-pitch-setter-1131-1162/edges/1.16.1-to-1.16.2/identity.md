# Source identity correction for the 1.16.1 → 1.16.2 pitch report

This note binds to the original report, which remains unchanged:

- Original report: `report.md`
- Original report SHA-256: `a07f7a719687b5d4c16282b6cc7de7ed8667f47e2a1783ca35720962abbd97dc`

## Corrected source-provenance conclusion

The original report's caveat that exact source publication and Mojmap build identity were unverified was incorrect. The canonical version-root sidecars exist under `decompiled_minecraft/1.16.1/` and `decompiled_minecraft/1.16.2/`; they are not nested under the `mojmap/` source directory. Both `mojmap.ready.json` markers identify the exact version and `mapping: mojmap`, with `status: ready`. Each is paired with `mojmap.provenance.json`, `mojmap.sources.sha256`, `artifacts.sha256`, and `movement-diagnostics.txt`.

The readiness marker's recorded hashes match the local sidecar bytes:

| Release | Ready-marker SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 (recorded = local) | Artifact-manifest SHA-256 (recorded = local) | Diagnostics SHA-256 (recorded = local) |
|---|---|---|---|---|---|
| 1.16.1 | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` | `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` | `3a7c0831f5c6d4b5d3bc8aef2b7ef88f0c1ee7e3981379ea0181d77733a4a827` |
| 1.16.2 | `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214` | `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108` | `a14aaa94b80b2783eb8903f2eaff520c9f623067df4d376480ca87cd26768902` |

The paired provenance records identify versions `1.16.1` and `1.16.2`, namespace and mapping option `mojmap`, one exact decompile run for batch `1.16.1,1.16.2`, and the recorded toolchain: Tiny Remapper 0.14.1, Mapping IO 0.9.1, Vineflower 1.12.0, Java 25.0.3+9-LTS, Gson 2.14.0, ASM 9.10.1, Gradle 9.7.1. The artifact manifests include the per-release raw client, client mappings, and mapped client archive hashes. In particular:

- 1.16.1 client: `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`; mappings: `6cc374f819bca44d940f8912044b07bdac3e240a02e37117ae1d4ad63c21409b`; mapped client archive: `f92d3ad192db28f84522436cc12bb92498ef3b8090f88e906d56d1fb8744490d`.
- 1.16.2 client: `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`; mappings: `c95a7046ab43371cc476b8b51978c6f2155c62f4cc5756b05c8ef81e0f11691f`; mapped client archive: `559df3579e366e11884d733f7a06530ee3f174b1eadeb8051f8492f6103b6d90`.

## Cited source bytes rechecked

For each release, the source manifest records the same SHA-256 as the current local file for all three files relied on by the original report:

| Release | Source file | SHA-256; local bytes match manifest |
|---|---|---|
| 1.16.1 | `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` |
| 1.16.1 | `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| 1.16.1 | `net/minecraft/util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |
| 1.16.2 | `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| 1.16.2 | `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| 1.16.2 | `net/minecraft/util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |

Conclusion: the local source bytes cited by `report.md` are bound to ready Mojmap release-root records and their hash-checked source/artifact manifests. The comparison conclusion itself remains bounded to the methods and dependencies documented in `report.md`.
