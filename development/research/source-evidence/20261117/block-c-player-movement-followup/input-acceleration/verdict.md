# Independent source audit verdict r2

**Terminal result: COMPLETED — ACCEPTED.** This verdict accepts the specific immutable comparison below after independently checking its corrected ordinary-input coordinate witness and nested provenance claims. It is source acceptance only; it does not establish runtime parity.

## Bound evidence

- Comparison `input-acceleration-source/comparison-r2.md` SHA-256: `8a80684968fa628f6306f1b1f5a641f66d621c25cdea8389faa7be4bb002f43d`
- 1.14.1 Yarn nested provenance SHA-256: `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`
- 1.14.2 Yarn nested provenance SHA-256: `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`
- 1.14.3 Yarn nested provenance SHA-256: `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`

## Audit result

The corrected axis claim is supported by the exact call paths. In 1.13.2, `LivingEntity.tick()` calls `moveRelative(this.sidewaysSpeed, this.verticalSpeed, this.forwardSpeed)`, and its land branch forwards the three parameters, in that order, to `updateVelocity`. Ordinary horizontal input therefore reaches the helper as X/Y/Z = `(sidewaysSpeed, verticalSpeed, forwardSpeed)`; Y is zero. In 1.14.1, the client similarly constructs `new Vec3d(this.sidewaysSpeed, this.upwardSpeed, this.forwardSpeed)` before `LivingEntity.travel` invokes `Entity.updateVelocity`. A non-sneaking forward+left digital input, without item-use slowdown and in ordinary land travel, is a reachable `(1, 0, 1)` witness for the two helper arithmetic paths.

The nested 1.14.1–1.14.3 Yarn provenance manifests bind exact client, mapping, intermediary, tools/runtime and source hashes. I verified each manifest's `Entity.java`, `Vec3d.java`, `KeyboardInput.java`, and `Input.java` hashes against its canonical nested source files. I also verified the cached client JAR, version JSON, Yarn mapping JAR and Intermediary JAR hashes against the corresponding manifest input hashes. The listed source digests and manifest identities in comparison-r2 match. The 1.14.1–1.14.3 helper and keyboard bodies are consistent with the comparison's no-change claim; the distinct 1.14.2/1.14.3 `Input` predicate is accurately scoped separately.

The source evidence supports the described legacy float helper versus 1.14 double-vector helper, strict cutoff/normalization gates, operation order, yaw expressions, and bounded patch statement: the change is present at 1.14.0 and remains present through the inspected 1.14.1–1.14.4 releases. No earlier cutover is claimed. The comparison's explicit scope exclusions and statement that source review does not establish runtime parity are appropriate.

No product files, tests, runtime, or source generation were changed or run. The earlier `verdict.md` remains preserved as the historical verdict for the prior comparison revision.