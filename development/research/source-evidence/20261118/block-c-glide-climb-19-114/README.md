# Early Elytra climbable fall-distance write (2026-11-18)

This archive binds 17 exact adjacent-source comparisons from 1.9 through 1.14 for the same narrow operation: an ordinary dry local player on foot at historically supported ladder/vine feet, with Elytra fall-flying already active. The reviewed glide branch uses the strict pre-move `velocityY > -0.5` condition to write `fallDistance = 1.0F`. Every listed verdict accepts no difference for its own bounded pair and evidence.

This is not full movement-pair coverage, an implementation or introduction-boundary claim, or runtime parity. Flight startup/launch, final-contact/end-of-tick state, external A/B effects, ordinary-branch contact/friction geometry, and actual collision outcomes remain separate.

| Pair | Frozen source input | Input SHA-256 | Independent verdict SHA-256 |
|---|---|---|---|
| 1.9→1.9.1 | `report.md` | `4a9c3cc1bf678b8ba1803deb14ce58e0cf183b2d2b652114e3df4cb8802f2a6a` | `6f03782eb43fa32f04ae0870555c62b3a2e0446db4d657b874c4a78efae618c8` |
| 1.9.1→1.9.2 | `report.md` | `0d54ffdfb2a74f4e0d3e75e1a066d56df910fdda6a0ed2176f30540418ab599a` | `67d2ef419146f0d317ffcd436930cdf025d0adb23ddfe8c1f8769a3173ab2617` |
| 1.9.2→1.9.3 | `report.md` | `68b747fb3067926b53b62886a5a6fa697066b4109a86958f47e16f62f0de37c7` | `f098d851730fbb4990efdb0e68a9d84773f5c107735f58caa03f6d1f4bea87ec` |
| 1.9.3→1.9.4 | `report.md` | `f73b01e80aeeef8ba20eb390bbecd673cb7f507a0033b6362acd5261ab6b1200` | `800c55dcd5d7138bbe2cab256e8576a2068c4f7a38629544061df61ee5a1e002` |
| 1.9.4→1.10 | `report.md` | `4d28790a0b2907bc9c6f58f61cdbc0648fb0844739e36dfec03ef48491a66e47` | `9f9b32b33430d9ac6f4b8467de3a21f8684992bac54f75bbfda8a0a35011b3e4` |
| 1.10→1.10.1 | `report.md` | `8c7b53ee68a29d9a829778ee23110b93f8b7a0b2d56b64721fea7e2fe20a634b` | `9a8678605bb52ecf5d31ec99a61ba4e568555a1eecd223981993705202baedf2` |
| 1.10.1→1.10.2 | `report.md` | `27b87025c5d29c0745b8828220ed0eeaef4c0d112e4b7f5beb687d294fd155d1` | `1d5c507c0872ab36bd89c13ccd7866b955b384d0638033dc5a5fefeb81bd458e` |
| 1.10.2→1.11 | `report.md` | `25e9bd11076455fa6e2047342b39cd31a7adf365281382bc4d2ba769ce44eed0` | `f9028a7aa10b0153284a9741e9d003d3074b6f1e9349ac55b1f576770a71f943` |
| 1.11→1.11.1 | `report.md` | `9ea7c07c412bdb42651033c338b98dfc24c324cb3007ccdaa5933058f11f2769` | `cb9076e28525cb4fc1eef7697bbf98340dea65212a959d0e207f4c8459e82f4f` |
| 1.11.1→1.11.2 | `report.md` | `afdc843d93258c541be01f72366751017ebd5e8be1303cd115909fb2e52e0e1d` | `94624a01ac7eaeeb23d9274bd3f94c4d0ec1e7ef094093b446c02089758ea36c` |
| 1.11.2→1.12 | `exact-1.12-comparison.md` | `732e75643ff9f591f8dbb6eb21be95717902d66e8a321c63a912aeed6416213e` | `25b2458705d6dcf1dfc43caf78aa096fc34d5692b26e326080b8c52fbeebe605` |
| 1.12→1.12.1 | `report.md` | `3366c546a958bc60ae98d9ac86dbe291e477f87005a33508772d934bf2dd7d13` | `b95f2f4af7e3ffda98ff43f17e51952980cc955d35817d4a37a2d6eb888adc6b` |
| 1.12.1→1.12.2 | `report.md` | `2a4ca2936e1cd1d40d5d961f269c723fa6a65659034fae820fde1e5d14a5f8be` | `ed53ca79f43cfb254a3ccbe1679fabc6f79f831179f136bcfba36f9ecdb43f0e` |
| 1.12.2→1.13 | `exact-1.13-comparison.md` | `ae88ea7b880c1dc0d1a6eeb0e8fa7abe772a8719446247e69b39203ee0627a57` | `bcdbc67900048a5aa3b835dd6faca9f230c10e967a4c910709f9085be63bd1e4` |
| 1.13→1.13.1 | `report.md` | `d5a3e7747425b86c31f96f2c91debbde1f4c8072b3f77331e346d5f395c17fad` | `6a6e0f85d954ce017ee9979916cb4c04c7c4fb5265e60e80f21b144f8d248f6c` |
| 1.13.1→1.13.2 | `report.md` | `dd2b774ca3478e0beea2bb15e07c5aff274b04641f603e7e334b821c85d0c703` | `c0cff63a265d9f24bffbec1f0de45bad513c6ef14c9d9aba56fea205527dfca2` |
| 1.13.2→1.14 | `report.md` | `3ca6e02921b07fd9968d79012eee1fdaa46f362afc136585d1ccbeff0b2d1e48` | `cf95d2b8626fab6b66197926de1a191382a20381244cd792e8b80d00db34e97e` |

The reports preserve their exact source namespaces, provenance identities and pair-specific evidence. Audit qualifications remain attached to their bound report revisions: the 1.9→1.9.1 Elytra-start sound addition is audio-only; the 1.10.2→1.11 grounded-sneaking support probe is a separate B-lane scope; the 1.12.2→1.13 gravity expression and negative-pitch zero-horizontal-look guard are separate source deltas, not part of this fall-distance operation. Input-cache or derived-artifact availability is reported per pair and is not generalized across the archive.

For 1.11.2→1.12, only `exact-1.12-comparison.md` is included; the incorrect original report is excluded. For 1.12.2→1.13, only `exact-1.13-comparison.md` is included; the incorrect original report is excluded. Every verdict binds the exact archived input SHA above. All report and verdict bytes are frozen, and the scoped `.gitattributes` preserves their line endings.