# Independent audit: 26.2 SUPPRESSES_BOUNCE tag input

**Verdict: ACCEPT** — the bounded input reference is internally consistent and is bound to the exact pinned 26.2 client JAR and source tag declaration.

## Immutable inputs

- Input reference: `build/source-resource-inputs-20261117/movement-gates/26.1.2--26.2_suppresses_bounce_tag/input-reference.json`
- Reference SHA-256: `e8ec85f81c3f0b187bdde06344e41b8be7b41cc7aee3745753f547d8248eaf0f`
- Source report: `build/later-source-20261116/26.1.2--26.2/slime_bounce_transition_revision_r2/coverage-r2.md`, SHA-256 `d6fd005ad2fd60c3d10645036c5286063eb645808bba560e776301004390453f`
- Source audit: `build/later-source-20261116/26.1.2--26.2/slime_bounce_transition_r2_audit/verdict-r2.md`, SHA-256 `aa55c0770a4ac1ae80d0f2764028946035d03d8e3d7743e7e9b36b30a88c505f`

## Source identity and tag crosswalk

The reference hash matches the file. 26.2 is the declared `unobfuscated` source publication: `unobfuscated.ready.json` SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` says `status=ready`, `versionId=26.2`, `mapping=unobfuscated`, and binds `unobfuscated.sources.sha256` digest `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` and `artifacts.sha256` digest `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`. The provenance file SHA-256 is `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`; it declares `versionId=26.2` and `namespace=unobfuscated`. Both manifest digests match the ready marker and on-disk files.

`BlockTags.java` has SHA-256 `9834ecbe2facd79cf3d9fc79babd452ed0e000684029b2bb5e9f90aa4f51529e`, matching its source-manifest row. It declares `SUPPRESSES_BOUNCE = create("suppresses_bounce")`; its `create` helper calls `Identifier.withDefaultNamespace(name)`. The hash-bound `Identifier.java` is `053f2c75971305a75b350e529dbef832a5175cd388991831d4ba0c6e340faac6`; `withDefaultNamespace` constructs an identifier in the `minecraft` namespace. Therefore the source key maps to `minecraft:suppresses_bounce` and the singular resource path `data/minecraft/tags/block/suppresses_bounce.json`.

## Pinned archive entry

The artifact manifest file hash is `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`. Its exact `26.2/client.jar` row pins SHA-256 `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`; the cached JAR at the reference path has that SHA-256 and size 39,193,383 bytes. Its SHA-1 is `2dc72797acbc1b63fc16a11c4ac393605f453754`, matching the official metadata's client SHA-1 and URL. The metadata file hash is `8d009c6ed0b7b8c65bfff515a1ff8b12cc8df72627bb0e355ecbce9f7bc34f9c`.

The pinned archive contains exactly one entry at `data/minecraft/tags/block/suppresses_bounce.json`; the plural `data/minecraft/tags/blocks/suppresses_bounce.json` entry is absent. The singular entry is 49 bytes and SHA-256 `a477a87ac4bcb97971cb0b445f4cc9b6b8e02cd31ba3d01bc842b17a6a8477a8`. Its JSON has `values: ["minecraft:honey_block"]` and no `replace` field. The extracted copy has the same 49 bytes, digest, and parsed payload. `minecraft:slime_block` is absent from this pinned payload.

The provenance sidecar does not record the client JAR digest or its local cache path; those values are verified here against the repository artifact pin and official metadata SHA-1. The conclusion is limited to this pinned archive entry and the source identifier crosswalk. It makes no claim about datapack overrides, reload state, runtime/effective-world membership, or behavior.
