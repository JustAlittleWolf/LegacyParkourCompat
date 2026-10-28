# Water early-range product wiring review

**Verdict: ACCEPT.** Independent product review of commit `8569ee709e6826108d2565cc1f58642842a4bb81` against parent `e486c0082e9214e62729ea9dc1b04b407d4de5e0` on `fix/water-body-contact-scan-20261028-r4`.

## Scope and basis

The commit changes 13 product files (67 insertions, 160 deletions): five obsolete native water contact/flow provider deletions and source-range/Javadoc edits in eight files. The full commit diff was read. Review also traced `ChangeResolver`, `ParkourVersion`, all affected water hook providers, `WaterScan`, `EntityWaterMixin`, and the current/contact provider implementations. No code was edited; no build, tests, game, or runtime were run.

The accepted evidence was rebound by SHA-256 against the local `.native-source/20261028` reports:

- `liquid-scan-18-boundary/REPORT.md`: `8713c683ab2550b831de03d1dcea024fdc63277c43c81ecebc96d4114fc3ba9c`
- `180181-water-raw-bytecode/REPORT.md`: `3c43839c0e9e91efe6a4ec60074e17c78aff0bed43bf7f234724704cbd2d5eca`; its independent flow/body audit `180181-water-raw-bytecode-audit/audit.md`: `ca546e51f6507749fccd1ace964c95386366d6109307d0ae011a4a601ca673be`
- `180181-water-raw-bytecode/contact-semantics-supplement.md`: `3467a47679c3c10af2f3c9a63548b1a36e5c9d55ccd1695810f69f20012923c1`; independent audit `180181-water-raw-bytecode-audit/contact-semantics-audit.md`: `9e1a018a8e5c93f4eb88e437599128cc61d02f7a44a362aa55798d86b0a75b2b`
- Tiny attribution correction `180181-water-raw-bytecode/contact-semantics-supplement-revision-001.md`: `f2efb6bdcec1ba54458605b1815cb4253b72a768303f9a8cb60b9d0ba9e2fb49`

The report and audits bound the 1.8.2–1.8.9 source identity, raw 1.8.0/1.8.1 operation, contact query shape, area/chunk admission, liquid height, per-cell flow, player push gate, and the Tiny attribution correction. Their limits remain bounded to those operations and named releases; they do not claim runtime parity.

## Resolver/provider trace

`ChangeResolver` filters each hook to providers whose `emulates` is the selected profile or newer, then selects the earliest such provider. With the commit deletions and the existing annotated providers, the affected resolution is:

| Selected profile | Body contact | Contact bound/admission | Flow bound/admission | Current hook outcome |
|---|---|---|---|---|
| `V1_8` (1.8.0–1.8.4) | `LegacyWaterBodyContact` (`V1_8_9`) | Legacy (`V1_8_9`) | Legacy (`V1_8_9`) | `WaterCurrent` (`V1_12`) is enabled by resolved historical flow bounds/admission |
| `V1_8_5` (1.8.5–1.8.8) | Same legacy provider | Same legacy providers | Same legacy providers | Enabled by the same flow resolution |
| `V1_8_9` | Same legacy provider | Same legacy providers | Same legacy providers | Enabled by the same flow resolution |
| `V1_9` | Native (`V1_9`) | No historical provider; native bounds/admission | No historical provider; native bounds/admission | Provider exists at `V1_12`, but `WaterScan.currentResolved` is false without a selected contact probe or resolved legacy flow scan |
| `V1_10` through `V1_11_1` | Native (`V1_9`) | Native fallback | Native fallback | Same disabled path as `V1_9` |
| `V1_11_2` | Native (`V1_11_2`) | Native fallback | Native fallback | Same disabled path |
| `V1_12` | Historical (`V1_12`) | Native ceil/default admission | Native ceil/default admission | Enabled through the selected contact probe |
| `V1_13+` | No historical provider; native tracker | Native | Native | No eligible current provider |

This confirms that removal of the five `V1_8_5` barriers lets all five 1.8 scan operations resolve to their `V1_8_9` implementations throughout 1.8.0–1.8.9. The earlier `.5–.8` contact and flow path is no longer fenced to native. The 1.9 native body-contact provider still closes that body-contact range; the 1.8-specific bound/admission hooks naturally stop resolving there. Later profiles retain their independently resolved native or 1.12 contact behavior.

The current hook remains coupled to a supported scan domain: in 1.8 profiles, `WaterScan.currentResolved` requires resolved historical flow bounds/admission; in 1.12 it requires the selected contact probe. At 1.9–1.11.2, the later current provider alone does not replace native current. `WaterScan` computes its impulse once after the union scan; the mixin adds it at the existing `updateFluidInteraction` contact-expression seam before returning the historical contact result. The commit changes no scan loop, bounds arithmetic, domain flags, mixin injection, or push timing.

Modern waterlogged/non-`LiquidBlock` water retains the existing fallback: when encountered in a replaced contact/native-feature domain, contact replacement is declined; in flow/native-feature domains, current replacement is declined. The mixin returns the native tracker result whenever contact replacement is declined. These paths are outside the commit diff and remain unchanged.

## Six review angles

1. **Requirement:** No deviation found. The five removed providers are precisely the old range fences; edits bind the expanded comments to the added exact-source evidence.
2. **Correctness and edge cases:** No functional issue found. Provider selection follows the resolver’s closest-applicable rule, with the intended 1.8.0–1.8.9 interval and 1.9+ native contact/bounds fallback.
3. **Missing work:** No missing hook, mixin, or call-site update found. Repository-wide provider search found no remaining `V1_8_5` water contact/flow native fence. The existing current gate and modern-feature fallback remain connected.
4. **Conventions and duplication:** No issue found. Existing separate hooks remain separately resolved; no catalog, duplicate loop, or new provider path was introduced.
5. **Permissions and visibility:** No access-control change. Observable behavior intentionally expands for 1.8.0–1.8.8 to match the accepted bounded historical scan behavior.
6. **Security:** No change introduces an input, authorization, path, credential, deserialization, or sensitive-log exposure.

## Findings and limits

**No functional findings.** No probable or unverified product defect was identified. Runtime parity and compilation were not verified, as required by the assignment; the source reports expressly do not claim runtime parity.

Next step: proceed with the integration/build owner’s authorized compilation workflow when the broader integration is ready; this review does not substitute for that build or for runtime parity evidence.
