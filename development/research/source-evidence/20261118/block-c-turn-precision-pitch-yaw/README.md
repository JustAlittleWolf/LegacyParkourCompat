# Direct turn precision: bounded pitch and yaw source evidence

This archive preserves two exact 1.16.5→1.17 direct-turn comparisons and two fixed-formula destination rechecks against 26.2. It covers finite in-range ordinary local turn inputs and the cited movement consumers. It is separate from saturation/range eligibility, mouse callback batching, and device/event preprocessing. It does not establish a product version range or runtime parity.

| Scope | Frozen source report SHA-256 | Independent verdict SHA-256 | Corrections / notes |
| --- | --- | --- | --- |
| Pitch, 1.16.5→1.17 | `f94d39bf82443713e9ae0fdbb6f9b835aea0a38d536f0a19012d5eadb6546525` | `9882f4b790f6183945cfd1cd96c9670d02fd58548e76c5969de71e49db7d318f` | `identity-correction.md` `76e863d515c3f200d7292c1a8dd8c21259b2bfee222a6dd488bd917ad4ee991e` |
| Pitch, 1.16.5→26.2 | `fd9f8cefc4299e7cb3c115d11aacc18d6ee5b89497b676a76adb2a685daabd75` | `daf002a0331adea131de6bb6e62d914d575164f321940787db9ac6114eddc46a` | Fixed-formula witness only; retain exact 26.2 alias and ready-output caveat from verdict. |
| Yaw, 1.16.5→1.17 | `8b862816c21c161606ed6c3b62172b7a969b69586e9f04f893ac515703c821c1` | `2bf8217436772233d8e9efd0753af108dd4cb86083abca2bc31cb0a523ef2605` (`exact-verdict.md` in scratch) | `correction.md` `3c867ee9bf12a3b1ca982176ac1dbe99e744e83055c9659af5a5c0dcc8a8fe09`; corrected witness changes X, with Z unchanged. Exact nested 1.17 source identity is accepted. |
| Yaw, 1.16.5→26.2 | `a510c56eb06f211b44e0fd3f7107fd4fedee84c52f9c5a99e6b82fe74304f944` | `69f0ccda6361e03666c99641158eca493d0c0865801b9c222795584888e421ee` | Fixed modern-kernel finite direct-turn witness and actual consumer; not shared mouse preprocessing equivalence. |

## Binding and scope notes

- Each independent verdict contains the SHA-256 of its exact source report. The pitch identity correction names the exact report and verdict hashes; the yaw correction likewise names the exact report and exact-verdict hashes.
- The original yaw review file named `verdict.md` compared against 1.17.1 and is not archived. The accepted 1.16.5→1.17 artifact is the sibling `exact-verdict.md`; this archive does not transfer the obsolete review's identity or conclusions.
- The pitch correction narrows the sensitivity-0.5 sensitivity observation: `e=4.000000357627879` is an exact direct-method witness, not a lawful callback sensitivity. Its independent lawful sensitivity-1.0, +1 pixel callback witness is `e=4.096000411987318` and changes the cited dry active Elytra request. Keep this distinct from saturation/range behavior.
- The yaw correction preserves the verified X-component change and says Z is unchanged for its cited witness. The endpoint yaw verdict concerns a finite direct-turn input and consumer; it does not prove identical event preprocessing.
- The pitch endpoint comparison is source-derived and keeps the exact unobfuscated 26.2 alias and ready-output provenance qualification in the signed verdict. No runtime claim is made.
- Frozen evidence files are copied byte-for-byte. No product, backlog, shared source, build, test, runtime, or network operation was performed.


- The yaw 1.16.5→26.2 report's received handoff digest was only 63 hexadecimal characters. The frozen report is 64-byte-hash valid at 510c56eb06f211b44e0fd3f7107fd4fedee84c52f9c5a99e6b82fe74304f944, and the signed independent verdict embeds that exact digest; the archive records the literal bound value.
- git diff --cached --check reports preserved trailing spaces in these original files: dges/pitch-1165-to-117/verdict.md, dges/yaw-1165-to-117/correction.md, dges/yaw-1165-to-117/report.md, dges/yaw-1165-to-117/verdict.md, dges/yaw-1165-to-262/report.md, and dges/yaw-1165-to-262/verdict.md. They remain unchanged to preserve accepted source identities.
Base: `aae9c86464638cb5ed7faa535c92569b89484906`.