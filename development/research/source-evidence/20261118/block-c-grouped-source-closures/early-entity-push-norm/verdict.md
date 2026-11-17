# Independent source audit: ordinary entity-push sqrt precision

## Verdict

**ACCEPTED for the bounded claim.** The comparison's claim is confirmed: in every sampled release from 1.9 through 1.16.5, `Entity.push(Entity)` assigns a float-returning square-root helper result to the double local `f`, rounding `sqrt(f)` to binary32 before widening it back to double. In exact 1.17 and 1.17.1, that operation is `f = Math.sqrt(f)`, which keeps binary64 precision. The exact 1.17 source pins the first stable release endpoint as already using the direct double result. Therefore, the sampled release boundary is old behavior at 1.16.5 and new behavior by 1.17; snapshot-level introduction is outside this audit.

For the sampled implementations, threshold comparison and `absMax` input are unchanged: `f` is the double absolute maximum of the horizontal deltas, and normalization is gated by `f >= 0.01F`. `absMax` conditionally negates its first argument, then its second, then returns the greater value. Following the sqrt, the code divides both deltas by `f`, computes `g = 1.0 / f`, caps `g` at 1.0, multiplies each delta by `g`, then multiplies by `0.05F`. This confirms the precision difference is localized to the normalization square root within the bounded ordinary finite calculation.

## Exact source review

The input comparison was read from the supplied immutable file and its SHA-256 matched exactly: `04feace810548c40c1f04f415084d988f439e1e711f37b2ddfeb27b753d549ba`.

All source files below were read only from the primary local `decompiled_minecraft/` tree. Each listed source hash matched its local `*.sources.sha256` inventory, except 1.9 and 1.17, whose exact source hashes matched their nested `.decompile-provenance.json` `sources` entries. The release-level provenance JSON hashes identify the local endpoint publications; the two nested-manifest identities are also included.

| Release | Mapping | `Entity.push(Entity)` / sqrt evidence | SHA-256: `Entity.java` | SHA-256: helper source | Local provenance identity |
|---|---|---|---|---|---|
| 1.9 | Ornithe Feather | `Entity.java:1057–1088`; `MathHelper.sqrt(double):27–29` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | nested manifest `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |
| 1.10.2 | Ornithe Feather | `Entity.java:1085–1116`; `MathHelper.sqrt(double):27–29` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | outer manifest `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6` |
| 1.11.2 | Ornithe Feather | `Entity.java:1153–1184`; `MathHelper.sqrt(double):27–29` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` | outer manifest `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6` |
| 1.12.2 | Ornithe Feather | `Entity.java:1176–1207`; `MathHelper.sqrt(double):27–29` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` | outer manifest `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` |
| 1.13.2 | Ornithe Feather | `Entity.java:1186–1217`; `MathHelper.sqrt(double):36–38` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` | outer manifest `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` |
| 1.14.4 | Mojmap | `Entity.java:1188–1219`; `Mth.sqrt(double):37–39` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `57e0babfbc9ce864e539c7632ebb72c2e14a208c2db1c3a1bfc16ee0237ae576` | outer manifest `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` |
| 1.15.2 | Mojmap | `Entity.java:1156–1187`; `Mth.sqrt(double):37–39` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` | outer manifest `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` |
| 1.16.5 | Mojmap | `Entity.java:1180–1211`; `Mth.sqrt(double):37–39` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` | outer manifest `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d` |
| 1.17 | Mojmap | `Entity.java:1276–1305`, direct `Math.sqrt` at 1283; `Mth.absMax(double,double):152–162` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` | exact nested manifest `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357` |
| 1.17.1 | Mojmap | `Entity.java:1278–1307`, direct `Math.sqrt` at 1285; `Mth.absMax(double,double):152–162` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` | outer manifest `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333`; exact 1.17 nested source map above |

The checked helper bodies for 1.9–1.16.5 return `(float)Math.sqrt(x)` / `(float)Math.sqrt(d)`. In 1.17 and 1.17.1, the enclosing entity method invokes `Math.sqrt(f)` directly; the helper file still supplies the unchanged double `absMax` operation. Source line references and full-file hashes above bound the reviewed methods.

## Numeric witness

For finite horizontal deltas `d = 0.3` and `e = 0.0`, the common gate passes. The historical helper path widens `sqrt(0.3)` rounded to float (`0.547722578048706`); direct `Math.sqrt` yields double `0.5477225575051661`. Following the source sequence `d /= f; g = 1.0 / f; d *= g; d *= 0.05F`, the pre-push-speed-factor delta magnitudes are respectively `0.04999999699433813` and `0.050000000745058074` (ordinary IEEE-754 binary64 operations with the source's binary32 `0.05F` constant widened for multiplication). The difference is source-derived arithmetic, not a runtime measurement.

## Bounds

This verdict covers only the candidate-vs-pusher normalization arithmetic in `Entity.push(Entity)` for the exact sampled releases 1.9, 1.10.2, 1.11.2, 1.12.2, 1.13.2, 1.14.4, 1.15.2, 1.16.5, 1.17 and 1.17.1, for ordinary finite horizontal deltas. It does not extend reachability, infer every patch boundary, inspect non-finite inputs, attack knockback, team collision rules, climbing pushability, riding/sleeping cases, or claim implementation, tests, or runtime parity. No product files, Git history, wiki material, tests, runtime, or source generation were used.
