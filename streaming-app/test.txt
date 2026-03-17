#!/usr/bin/env python3
"""
Java Code Extractor Script - Windows 11 Compatible
==================================================
Extracts all Java files from src/main/java and src/test/java directories
and combines them into a single text file with proper separators and metadata.
Fixed for Windows path handling issues.

Usage:
    python extract_java_code.py

Output:
    - all_java_code.txt: Contains all Java code with separators
    - extraction_summary.txt: Summary of extraction process
"""

import os
import sys
import glob
from pathlib import Path
from datetime import datetime
import argparse

# Configuration
DEFAULT_OUTPUT_FILE = "all_java_code.txt"
DEFAULT_SUMMARY_FILE = "extraction_summary.txt"
SEPARATOR = "=" * 80
FILE_SEPARATOR = "-" * 60

def normalize_path(path):
    """Normalize path for consistent display"""
    return str(path).replace('\\', '/')

def get_display_path(file_path, base_dirs=None):
    """Get a clean display path without relative_to() issues"""
    normalized = normalize_path(file_path)
    
    # If base_dirs provided, try to make path relative to one of them
    if base_dirs:
        for base_dir in base_dirs:
            base_normalized = normalize_path(base_dir)
            if normalized.startswith(base_normalized):
                return normalized[len(base_normalized):].lstrip('/')
    
    # If file is in current directory structure, show relative path
    cwd_str = normalize_path(os.getcwd())
    if normalized.startswith(cwd_str):
        return normalized[len(cwd_str):].lstrip('/')
    
    # Otherwise return the normalized path
    return normalized

def count_lines(content):
    """Count non-empty lines in content"""
    return len([line for line in content.split('\n') if line.strip()])

def get_file_size(filepath):
    """Get file size in human readable format"""
    try:
        size = os.path.getsize(filepath)
        for unit in ['B', 'KB', 'MB']:
            if size < 1024.0:
                return f"{size:.1f} {unit}"
            size /= 1024.0
        return f"{size:.1f} GB"
    except OSError:
        return "Unknown"

def extract_package_name(content):
    """Extract package name from Java file content"""
    lines = content.split('\n')
    for line in lines:
        line = line.strip()
        if line.startswith('package ') and line.endswith(';'):
            return line[8:-1].strip()  # Remove 'package ' and ';'
    return "No package declaration"

def extract_class_names(content):
    """Extract class/interface names from Java content"""
    classes = []
    lines = content.split('\n')
    
    for line in lines:
        line = line.strip()
        # Look for class declarations
        if ('class ' in line or 'interface ' in line or 'enum ' in line) and \
           ('public' in line or 'private' in line or 'protected' in line or 
            line.startswith('class') or line.startswith('interface') or line.startswith('enum')):
            
            # Extract class name
            for keyword in ['class ', 'interface ', 'enum ']:
                if keyword in line:
                    parts = line.split(keyword)
                    if len(parts) > 1:
                        class_part = parts[1].split()[0]
                        # Remove generic parameters if any
                        class_name = class_part.split('<')[0].split('{')[0]
                        if class_name and class_name not in classes:
                            classes.append(class_name)
                    break
    
    return classes if classes else ["Unknown"]

def find_java_files(base_dirs):
    """Find all Java files in the given directories using glob (Windows-safe)"""
    java_files = []
    
    for base_dir in base_dirs:
        if not os.path.exists(base_dir):
            print(f"Warning: Directory {base_dir} does not exist")
            continue
            
        # Use glob for reliable file finding on Windows
        patterns = [
            os.path.join(base_dir, "**", "*.java"),
            os.path.join(base_dir, "*.java")  # Also check root of directory
        ]
        
        for pattern in patterns:
            found_files = glob.glob(pattern, recursive=True)
            for file_path in found_files:
                if os.path.isfile(file_path) and file_path not in java_files:
                    java_files.append(file_path)
    
    return sorted(java_files)

def determine_file_type(file_path):
    """Determine the type of Java file based on path and name"""
    path_str = str(file_path).lower()
    file_name = os.path.basename(file_path)
    
    if 'test' in path_str or 'Test' in file_name:
        return "Test"
    elif 'config' in path_str or 'Config' in file_name:
        return "Configuration"
    elif 'Controller' in file_name:
        return "Controller"
    elif 'Service' in file_name:
        return "Service"
    elif 'model' in path_str or 'Model' in file_name:
        return "Model"
    elif 'Producer' in file_name:
        return "Producer"
    elif 'Consumer' in file_name:
        return "Consumer"
    elif 'Stream' in file_name or 'Processor' in file_name:
        return "Stream Processor"
    elif 'Repository' in file_name:
        return "Repository"
    elif 'Entity' in file_name:
        return "Entity"
    else:
        return "Other"

def extract_java_code(source_dirs, output_file, summary_file, include_comments=True):
    """
    Extract all Java code from specified directories
    
    Args:
        source_dirs: List of directories to scan
        output_file: Output file for combined code
        summary_file: Summary file for extraction statistics
        include_comments: Whether to include comments in output
    """
    
    print(f"🚀 Starting Java Code Extraction...")
    print(f"📂 Scanning directories: {', '.join(source_dirs)}")
    print(f"📁 Current working directory: {os.getcwd()}")
    print(f"📝 Output file: {output_file}")
    print(f"📊 Summary file: {summary_file}")
    print(SEPARATOR)
    
    # Find all Java files
    all_files = find_java_files(source_dirs)
    
    if not all_files:
        print("❌ No Java files found in specified directories!")
        return
    
    print(f"✅ Total files found: {len(all_files)}")
    for source_dir in source_dirs:
        count = len([f for f in all_files if normalize_path(f).startswith(normalize_path(source_dir))])
        print(f"📁 {source_dir}: {count} files")
    
    print(SEPARATOR)
    
    # Initialize statistics
    stats = {
        'total_files': 0,
        'total_lines': 0,
        'total_size': 0,
        'main_files': 0,
        'test_files': 0,
        'directories': {},
        'file_types': {},
        'packages': set(),
        'classes': []
    }
    
    # Process files and write output
    with open(output_file, 'w', encoding='utf-8') as outfile:
        # Write header
        outfile.write(f"""
{SEPARATOR}
KAFKA STREAMS BANKING APPLICATION - COMPLETE SOURCE CODE
{SEPARATOR}
Generated on: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}
Total Files: {len(all_files)}
Source Directories: {', '.join(source_dirs)}
Working Directory: {os.getcwd()}
{SEPARATOR}

""")
        
        # Process each file
        for file_index, file_path in enumerate(all_files, 1):
            try:
                display_path = get_display_path(file_path, source_dirs)
                print(f"📄 Processing {file_index}/{len(all_files)}: {display_path}")
                
                # Read file content
                with open(file_path, 'r', encoding='utf-8', errors='ignore') as infile:
                    content = infile.read()
                
                # Update statistics
                try:
                    file_size = os.path.getsize(file_path)
                    stats['total_size'] += file_size
                except OSError:
                    file_size = 0
                
                line_count = count_lines(content)
                stats['total_files'] += 1
                stats['total_lines'] += line_count
                
                # Determine source directory
                source_dir = "Unknown"
                for src_dir in source_dirs:
                    if normalize_path(file_path).startswith(normalize_path(src_dir)):
                        source_dir = src_dir
                        break
                
                # Update directory stats
                stats['directories'][source_dir] = stats['directories'].get(source_dir, 0) + 1
                
                # Count main vs test files
                if 'main' in normalize_path(file_path):
                    stats['main_files'] += 1
                elif 'test' in normalize_path(file_path):
                    stats['test_files'] += 1
                
                # Extract metadata
                package_name = extract_package_name(content)
                class_names = extract_class_names(content)
                file_type = determine_file_type(file_path)
                
                # Track packages and classes
                if package_name != "No package declaration":
                    stats['packages'].add(package_name)
                stats['classes'].extend(class_names)
                stats['file_types'][file_type] = stats['file_types'].get(file_type, 0) + 1
                
                # Get file modification time
                try:
                    mod_time = datetime.fromtimestamp(os.path.getmtime(file_path)).strftime('%Y-%m-%d %H:%M:%S')
                except OSError:
                    mod_time = "Unknown"
                
                # Write file header
                outfile.write(f"""
{SEPARATOR}
FILE: {display_path}
{SEPARATOR}
📁 Source Directory: {source_dir}
📁 Full Path: {normalize_path(file_path)}
📦 Package: {package_name}
🏷️  File Type: {file_type}
📊 Lines of Code: {line_count}
📏 File Size: {get_file_size(file_path)}
🏗️  Classes/Interfaces: {', '.join(class_names)}
⏰ Last Modified: {mod_time}
{FILE_SEPARATOR}

""")
                
                # Process content if needed
                if not include_comments:
                    # Simple comment removal (basic implementation)
                    lines = content.split('\n')
                    filtered_lines = []
                    in_block_comment = False
                    
                    for line in lines:
                        line_stripped = line.strip()
                        
                        # Handle block comments
                        if '/*' in line_stripped:
                            in_block_comment = True
                        if '*/' in line_stripped:
                            in_block_comment = False
                            continue
                        if in_block_comment:
                            continue
                            
                        # Handle line comments
                        if not line_stripped.startswith('//'):
                            filtered_lines.append(line)
                    
                    content = '\n'.join(filtered_lines)
                
                # Write file content
                outfile.write(content)
                outfile.write(f"\n\n{FILE_SEPARATOR}\n")
                outfile.write(f"END OF FILE: {display_path}\n")
                outfile.write(f"{FILE_SEPARATOR}\n\n")
                
            except Exception as e:
                error_path = get_display_path(file_path, source_dirs)
                print(f"❌ Error processing {error_path}: {str(e)}")
                outfile.write(f"\n\nERROR PROCESSING FILE: {error_path}\nError: {str(e)}\n\n")
    
    # Write summary file
    write_summary_file(summary_file, all_files, source_dirs, stats)
    
    # Print completion summary
    print_completion_summary(stats, output_file, summary_file)

def write_summary_file(summary_file, all_files, source_dirs, stats):
    """Write the extraction summary file"""
    with open(summary_file, 'w', encoding='utf-8') as summary:
        summary.write(f"""
{SEPARATOR}
JAVA CODE EXTRACTION SUMMARY
{SEPARATOR}
Generated on: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}
Working Directory: {os.getcwd()}
Output File: {DEFAULT_OUTPUT_FILE}

📊 EXTRACTION STATISTICS
{FILE_SEPARATOR}
Total Files Processed: {stats['total_files']}
Total Lines of Code: {stats['total_lines']}
Total Size: {stats['total_size'] / 1024:.1f} KB

📁 DIRECTORY BREAKDOWN
{FILE_SEPARATOR}
""")
        
        for directory, file_count in stats['directories'].items():
            summary.write(f"{directory}: {file_count} files\n")
        
        summary.write(f"""
Main Source Files: {stats['main_files']}
Test Files: {stats['test_files']}

🏷️  FILE TYPE BREAKDOWN  
{FILE_SEPARATOR}
""")
        
        for file_type, count in sorted(stats['file_types'].items()):
            summary.write(f"{file_type}: {count} files\n")
        
        summary.write(f"""
📦 PACKAGES FOUND ({len(stats['packages'])})
{FILE_SEPARATOR}
""")
        
        for package in sorted(stats['packages']):
            summary.write(f"- {package}\n")
        
        summary.write(f"""
🏗️  CLASSES/INTERFACES FOUND ({len(set(stats['classes']))})
{FILE_SEPARATOR}
""")
        
        unique_classes = sorted(set(stats['classes']))
        for class_name in unique_classes:
            summary.write(f"- {class_name}\n")
        
        summary.write(f"""
📂 FILES PROCESSED
{FILE_SEPARATOR}
""")
        
        for file_path in all_files:
            display_path = get_display_path(file_path, source_dirs)
            summary.write(f"✅ {display_path}\n")

def print_completion_summary(stats, output_file, summary_file):
    """Print completion summary to console"""
    print(f"\n{SEPARATOR}")
    print(f"🎉 EXTRACTION COMPLETED SUCCESSFULLY!")
    print(f"{SEPARATOR}")
    print(f"📊 Files processed: {stats['total_files']}")
    print(f"📝 Lines of code: {stats['total_lines']:,}")
    print(f"📏 Total size: {stats['total_size'] / 1024:.1f} KB")
    print(f"📦 Packages found: {len(stats['packages'])}")
    print(f"🏗️  Classes found: {len(set(stats['classes']))}")
    print(f"\n📁 Output files:")
    print(f"   - {output_file}")
    print(f"   - {summary_file}")
    print(f"{SEPARATOR}")

def main():
    """Main function with command line argument support"""
    parser = argparse.ArgumentParser(
        description="Extract all Java code from source directories into a single file",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
    python extract_java_code.py
    python extract_java_code.py --output my_code.txt --summary my_summary.txt
    python extract_java_code.py --no-comments
    python extract_java_code.py --dirs src/main/java custom/src/directory
        """
    )
    
    parser.add_argument(
        '--dirs', 
        nargs='+', 
        default=['src/main/java', 'src/test/java'],
        help='Source directories to scan (default: src/main/java src/test/java)'
    )
    
    parser.add_argument(
        '--output', 
        default=DEFAULT_OUTPUT_FILE,
        help=f'Output file name (default: {DEFAULT_OUTPUT_FILE})'
    )
    
    parser.add_argument(
        '--summary', 
        default=DEFAULT_SUMMARY_FILE,
        help=f'Summary file name (default: {DEFAULT_SUMMARY_FILE})'
    )
    
    parser.add_argument(
        '--no-comments', 
        action='store_false', 
        dest='include_comments',
        help='Exclude comments from output (basic removal)'
    )
    
    args = parser.parse_args()
    
    # Validate directories exist
    valid_dirs = []
    for directory in args.dirs:
        if os.path.exists(directory):
            valid_dirs.append(directory)
        else:
            print(f"⚠️  Warning: Directory '{directory}' does not exist, skipping...")
    
    if not valid_dirs:
        print("❌ Error: No valid directories found!")
        print("💡 Make sure you're running this script from your project root directory")
        print("💡 And that src/main/java and/or src/test/java directories exist")
        sys.exit(1)
    
    # Start extraction
    try:
        extract_java_code(
            source_dirs=valid_dirs,
            output_file=args.output,
            summary_file=args.summary,
            include_comments=args.include_comments
        )
    except KeyboardInterrupt:
        print("\n🛑 Extraction interrupted by user")
        sys.exit(1)
    except Exception as e:
        print(f"❌ Unexpected error: {str(e)}")
        import traceback
        traceback.print_exc()
        sys.exit(1)

if __name__ == "__main__":
    main()