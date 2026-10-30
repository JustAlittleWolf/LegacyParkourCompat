# Independent audit: glide vertical-acceleration alias extension

## Binding and scope

- Audited `.native-source/20261030/ordinary-pool/glide-vertical-acceleration-aliases/source-extension.md`, SHA-256 `55c4700afef47ed9426f587de41e6d169ca8c9ec11ea71d46c93b3ecd841bea0`.
- Also applied `source-extension-correction-1.md`, SHA-256 `bf50b8ff9727bf990f6c14a6dd9c8bdcc054c976320dda52d723bca8bd691e21`, as the current narrow line anchors.
- Independently checked only the seven named canonical `LivingEntity.java` files and their adjacent/root provenance and source manifests.

## Verdict

The central operation-level finding is supported for all seven sampled releases. The reported source hashes match the files, and each cited line has the claimed lift narrowing and `velocityY += -0.08 + m * 0.06` expression. The corrected gate, narrowing, and acceleration line references match. The shared gate order is local/logical movement authority, water exclusion except flying players, lava exclusion except flying players, then `isFallFlying()`.

The type account is supported by the exact Java expressions: `i` and `m` are floats; `l`, `1.0`, `0.4`, `-0.08`, and `0.06` participate as doubles in the stated expressions. The parenthesized `m * (m * Math.min(...))` product is evaluated with double promotion and narrowed once by the outer `(float)`. The acceleration right-hand side is double-valued before compound assignment. The restricted source set does not contain the inherited `velocityY` field declaration, so this audit does not independently verify the report's claim that the field itself is double. The expression and gate claims are limited to the seven checked releases; they establish no unsampled release boundary.

Provenance is consistent: the 1.9, 1.12, and 1.12.1 embedded provenance JSON hashes match the report; their recorded `LivingEntity.java` source hashes match the independently hashed files. For 1.9.4, 1.10.2, 1.11.2, and 1.12.2, the named Feather Gen2 mapping artifact identities are present in root provenance and the source-manifest row matches the source file hash. No hash or mapping-identity discrepancy found.

## Correction required

The original report's sentence claiming that vertical drag comes before the subsequent dive and pull-up corrections reverses the observed source order. In every checked source, dive correction (`velocityY < 0.0 && j > 0.0`), pull-up correction (`i < 0.0F`), and horizontal alignment run after the acceleration; drag (`velocityX *= 0.99F`, `velocityY *= 0.98F`, `velocityZ *= 0.99F`) follows those corrections. Example: 1.12.2 lines 1441–1463. The corrected line-reference supplement does not amend this ordering sentence. This is outside the central acceleration-equivalence claim, but the sentence should be corrected before the extension is treated as fully accurate.

## Bounded open gates

This audit does not verify unsampled patch releases, the first introduction boundary, other glide guards or call sites, 1.8 fallback wiring, default-gravity behavior outside the sampled glide expression, runtime parity, or implementation readiness. The extension itself appropriately lists most of these limits; this audit makes no runtime or integration claim.
