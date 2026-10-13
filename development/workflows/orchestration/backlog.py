"""Entry-level access to the research backlog; Python standard library only."""

import argparse
from contextlib import contextmanager
from copy import deepcopy
import hashlib
import json
import os
import re
from pathlib import Path
import sys
import tempfile

ROOT = Path(__file__).resolve().parents[3]
BACKLOG = ROOT / 'development/research/backlog.json'
KEYS = {
    'findings': ('finding',),
    'coverage': ('pair',),
    'unresolvedClaims': ('pair', 'claim'),
    'implementationEvidenceGaps': ('change',),
    'excludedOperations': ('operation',),
}


def fail(message):
    raise ValueError(message)


def object_pairs(pairs):
    result = {}
    for key, value in pairs:
        if key in result:
            fail(f'Duplicate JSON key: {key}')
        result[key] = value
    return result


def decode(raw):
    return json.loads(raw, object_pairs_hook=object_pairs,
                      parse_constant=lambda value: fail(f'Invalid JSON number: {value}'))


def digest(value):
    raw = json.dumps(value, ensure_ascii=False, sort_keys=True, separators=(',', ':'))
    return hashlib.sha256(raw.encode('utf-8')).hexdigest()


def identity(section, record):
    values = [record.get(key) for key in KEYS[section]]
    if any(not isinstance(value, str) or not value or '::' in value for value in values):
        fail(f'{section}: expected nonempty identity fields {KEYS[section]} without ::')
    return '::'.join(values)


def local_file(value, finding=False):
    if not isinstance(value, str) or '\\' in value or Path(value).is_absolute() or '..' in Path(value).parts:
        fail(f'Expected repository-relative forward-slash path: {value!r}')
    path = (ROOT / value).resolve()
    if not path.is_relative_to(ROOT) or not path.is_file():
        fail(f'Missing or external repository file: {value}')
    if finding:
        parts = Path(value).parts
        if len(parts) != 5 or parts[:3] != ('development', 'research', 'findings'):
            fail(f'Invalid finding path: {value}')
        if not re.fullmatch(r'[0-9]+(?:\.[0-9]+)*--[0-9]+(?:\.[0-9]+)*', parts[3]):
            fail(f'Invalid version pair: {parts[3]}')
        if not re.fullmatch(r'[a-z0-9]+(?:-[a-z0-9]+)*\.md', parts[4]):
            fail(f'Invalid finding filename: {parts[4]}')


def validate(data, check_files=True):
    if not isinstance(data, dict):
        fail('Backlog must be an object')
    for section in KEYS:
        records = data.get(section)
        if not isinstance(records, list):
            fail(f'{section} must be an array')
        seen = set()
        for record in records:
            if not isinstance(record, dict):
                fail(f'{section} entries must be objects')
            key = identity(section, record)
            if key in seen:
                fail(f'Duplicate {section} identity: {key}')
            seen.add(key)
            if section == 'findings':
                if check_files:
                    local_file(record['finding'], finding=True)
                if record.get('implementation') not in ('open', 'partial', 'present'):
                    fail(f'{key}: implementation must be open, partial or present')
                if not isinstance(record.get('changes'), list):
                    fail(f'{key}: changes must be an array')
                if check_files:
                    for path in record['changes']:
                        local_file(path)
            if section == 'implementationEvidenceGaps' and check_files:
                local_file(record['change'])
            if 'openIssues' in record:
                allowed = (list, str) if section == 'unresolvedClaims' else list
                if not isinstance(record['openIssues'], allowed):
                    fail(f'{key}: invalid openIssues type')
    for key, value in data.items():
        if key not in KEYS and not isinstance(value, str):
            fail(f'Metadata {key} must be a string')


def load(check_files=True):
    raw = BACKLOG.read_bytes()
    data = decode(raw.decode('utf-8-sig'))
    validate(data, check_files=check_files)
    return raw, data


def get_record(data, section, key):
    if section == 'metadata':
        if key not in data or key in KEYS:
            fail(f'Unknown metadata key: {key}')
        return data[key]
    for record in data[section]:
        if identity(section, record) == key:
            return record
    fail(f'Unknown {section} identity: {key}')


def merge_patch(target, patch):
    if not isinstance(patch, dict):
        return deepcopy(patch)
    result = deepcopy(target) if isinstance(target, dict) else {}
    for key, value in patch.items():
        if value is None:
            result.pop(key, None)
        else:
            result[key] = merge_patch(result.get(key), value)
    return result


def encode(data):
    # One record per line preserves readable, localized Git diffs in the legacy store.
    lines = ['{']
    for number, (key, value) in enumerate(data.items()):
        prefix = '  ' + json.dumps(key) + ': '
        comma = ',' if number < len(data) - 1 else ''
        if key in KEYS:
            lines.append(prefix + '[')
            for index, record in enumerate(value):
                if key == 'coverage':
                    rendered = json.dumps(record, ensure_ascii=False, indent=2).splitlines()
                    lines.extend('    ' + line for line in rendered)
                    lines[-1] += ',' if index < len(value) - 1 else ''
                else:
                    lines.append('    ' + json.dumps(record, ensure_ascii=False)
                                 + (',' if index < len(value) - 1 else ''))
            lines.append('  ]' + comma)
        else:
            lines.append(prefix + json.dumps(value, ensure_ascii=False) + comma)
    return ('\n'.join(lines + ['}']) + '\n').encode('utf-8')


@contextmanager
def writer_lock():
    lock = BACKLOG.with_suffix('.json.lock')
    try:
        fd = os.open(lock, os.O_CREAT | os.O_EXCL | os.O_WRONLY)
    except FileExistsError:
        fail(f'Backlog writer lock exists: {lock}. Check its owner before removing a stale lock.')
    try:
        with os.fdopen(fd, 'w') as handle:
            handle.write(f'pid={os.getpid()}\n')
        yield
    finally:
        lock.unlink()


def save(data, original):
    temp_name = None
    try:
        with tempfile.NamedTemporaryFile(dir=BACKLOG.parent, prefix='.backlog-', suffix='.tmp', delete=False) as handle:
            temp_name = handle.name
            handle.write(encode(data))
            handle.flush()
            os.fsync(handle.fileno())
        if BACKLOG.read_bytes() != original:
            fail('Backlog changed during this operation; reread the record and retry')
        os.replace(temp_name, BACKLOG)
        temp_name = None
    finally:
        if temp_name is not None:
            Path(temp_name).unlink(missing_ok=True)


def summary(section, record):
    result = {'id': identity(section, record), 'revision': digest(record)}
    for key in ('implementation', 'sourceCoverage', 'dependencyClosure', 'independentCoverageAudit', 'nextAction', 'issue', 'decision'):
        if key in record:
            result[key] = record[key]
    if 'openIssues' in record:
        result['openIssueCount'] = len(record['openIssues']) if isinstance(record['openIssues'], list) else 1
    return result


def show(value):
    print(json.dumps(value, ensure_ascii=False, indent=2))


def input_file(name):
    return decode(sys.stdin.read() if name == '-' else Path(name).read_text(encoding='utf-8-sig'))


def modify(args):
    original, data = load()
    before = None if args.command == 'add' else get_record(data, args.section, args.id)
    if args.command != 'add' and args.expect != digest(before):
        fail('Stale record revision; show the record again and reconcile your update')
    if args.command == 'delete':
        if args.section not in ('unresolvedClaims', 'implementationEvidenceGaps'):
            fail('Only resolved temporary claims or implementation evidence gaps may be deleted')
        after = None
    else:
        patch = input_file(args.file)
        after = merge_patch(before, patch) if args.command == 'update' else patch
        if args.section == 'metadata':
            if not isinstance(after, str):
                fail('Metadata update file must contain a JSON string')
        elif not isinstance(after, dict) or identity(args.section, after) != args.id:
            fail('Record identity must match the requested ID and cannot be changed')
    if args.section == 'metadata':
        if args.command == 'add' and args.id in data:
            fail(f'Metadata already exists: {args.id}')
        data[args.id] = after
    elif args.command == 'add':
        if any(identity(args.section, row) == args.id for row in data[args.section]):
            fail(f'Record already exists: {args.id}')
        data[args.section].append(after)
    else:
        rows = data[args.section]
        index = next(i for i, row in enumerate(rows) if identity(args.section, row) == args.id)
        if args.command == 'delete':
            del rows[index]
        else:
            rows[index] = after
    validate(data)
    if args.apply:
        save(data, original)
    show({'applied': args.apply, 'section': args.section, 'id': args.id,
          'beforeRevision': digest(before) if before is not None else None,
          'revision': digest(after) if after is not None else None,
          'record': after})


def relocate(args):
    # A source-set migration necessarily makes old file references unavailable.
    # Only this explicit maintenance operation may read that intermediate state.
    original, data = load(check_files=False)
    revision = digest(data)
    if args.apply and args.expect != revision:
        fail('Stale store revision; preview relocation again')
    mapping = input_file(args.file)
    if not isinstance(mapping, dict) or not mapping:
        fail('Relocation needs a nonempty object mapping old code paths to new paths')
    for old, new in mapping.items():
        if not isinstance(old, str) or not old.startswith('src/') or not old.endswith('.java'):
            fail('Relocation accepts exact Java source paths only')
        local_file(new)
        if not new.startswith('src/') or not new.endswith('.java') or old == new:
            fail('Relocation destinations must be different Java source paths')
    affected = []
    used = set()
    for section in ('findings', 'implementationEvidenceGaps'):
        for row in data[section]:
            old_id = identity(section, row)
            paths = row['changes'] if section == 'findings' else [row['change']]
            changed = [mapping.get(path, path) for path in paths]
            if changed != paths:
                used.update(path for path in paths if path in mapping)
                if section == 'findings':
                    row['changes'] = changed
                else:
                    row['change'] = changed[0]
                affected.append({'section': section, 'id': old_id,
                                 'revision': digest(row)})
    if not used:
        fail('Mapping does not relocate any current code reference')
    validate(data)
    if args.apply:
        save(data, original)
    show({'applied': args.apply, 'beforeRevision': revision,
          'relocatedPaths': len(used), 'affectedRecords': len(affected)})


def main():
    # Machine-facing JSON must use the same encoding when piped on Windows.
    sys.stdout.reconfigure(encoding='utf-8')
    sys.stderr.reconfigure(encoding='utf-8')
    parser = argparse.ArgumentParser(description=__doc__)
    commands = parser.add_subparsers(dest='command', required=True)
    commands.add_parser('validate', help='Validate structure, unique IDs and current file references')
    commands.add_parser('summary', help='Show counts, metadata and implementation totals')
    relocation = commands.add_parser('relocate-code', help='Atomically relocate exact current Java references after source moves')
    relocation.add_argument('--file', required=True)
    relocation.add_argument('--expect', help='Whole-store revision returned by relocation preview; required with --apply')
    relocation.add_argument('--apply', action='store_true')
    listing = commands.add_parser('list', help='Search a section; bounded summaries only')
    listing.add_argument('section', choices=KEYS)
    listing.add_argument('--search', default='')
    listing.add_argument('--status', choices=('open', 'partial', 'present'))
    listing.add_argument('--offset', type=int, default=0)
    listing.add_argument('--limit', type=int, default=20)
    reading = commands.add_parser('show', help='Read one entry and its revision')
    reading.add_argument('section', choices=[*KEYS, 'metadata'])
    reading.add_argument('id')
    reading.add_argument('--field', help='Return only this top-level field of the entry')
    for command in ('update', 'add', 'delete'):
        sub = commands.add_parser(command, help='Preview a change; --apply writes it (master only)')
        sub.add_argument('section', choices=[*KEYS, 'metadata'] if command in ('update', 'add') else KEYS)
        sub.add_argument('id')
        if command != 'delete':
            sub.add_argument('--file', required=True, help='JSON merge patch for update, full record for add; - reads stdin')
        if command != 'add':
            sub.add_argument('--expect', required=True, help='Revision returned by show/list')
        sub.add_argument('--apply', action='store_true')
    args = parser.parse_args()
    if args.command == 'relocate-code':
        if args.apply:
            with writer_lock():
                relocate(args)
        else:
            relocate(args)
        return
    if args.command in ('update', 'add', 'delete'):
        if args.apply:
            with writer_lock():
                modify(args)
        else:
            modify(args)
        return
    _, data = load()
    if args.command == 'validate':
        show({'valid': True, 'counts': {key: len(data[key]) for key in KEYS}})
    elif args.command == 'summary':
        show({'counts': {key: len(data[key]) for key in KEYS},
              'implementation': {state: sum(row['implementation'] == state for row in data['findings'])
                                 for state in ('open', 'partial', 'present')},
              'metadata': {key: value if len(value) <= 512 else value[:512] + f'… ({len(value)} characters; use show metadata)'
                           for key, value in data.items() if key not in KEYS}})
    elif args.command == 'list':
        if args.offset < 0 or not 1 <= args.limit <= 100:
            fail('offset must be nonnegative; limit must be 1 through 100')
        if args.status and args.section != 'findings':
            fail('--status applies only to findings')
        rows = [row for row in data[args.section]
                if args.search.casefold() in json.dumps(row, ensure_ascii=False).casefold()
                and (not args.status or row['implementation'] == args.status)]
        end = args.offset + args.limit
        show({'total': len(rows), 'offset': args.offset,
              'nextOffset': end if end < len(rows) else None,
              'records': [summary(args.section, row) for row in rows[args.offset:end]]})
    elif args.command == 'show':
        record = get_record(data, args.section, args.id)
        value = record
        if args.field:
            if not isinstance(record, dict) or args.field not in record:
                fail(f'Unknown field: {args.field}')
            value = record[args.field]
        show({'id': args.id, 'revision': digest(record), 'record': value})


if __name__ == '__main__':
    try:
        main()
    except (ValueError, OSError) as error:
        print(f'backlog: {error}', file=sys.stderr)
        sys.exit(1)
