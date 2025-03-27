# #!/bin/bash

# # Define variables
# SRC_DIR="src/main/java"
# OUT_DIR="out"
# JAR_FILE="uniquehands.jar"
# MAIN_CLASS="edu.canisius.csc213.project1.UniqueHands"
# MANIFEST="Manifest.txt"

# # Clean previous build
# echo "🧹 Cleaning previous build..."
# rm -rf $OUT_DIR
# mkdir -p $OUT_DIR
# rm -f $JAR_FILE

# # Compile Java files
# echo "🚀 Compiling Java files..."
# find $SRC_DIR -name "*.java" > sources.txt
# javac -d $OUT_DIR @sources.txt
# rm sources.txt

# # Create manifest file
# echo "Main-Class: $MAIN_CLASS" > $MANIFEST

# # Package into a JAR
# echo "📦 Creating JAR file..."
# jar cfm $JAR_FILE $MANIFEST -C $OUT_DIR .

# # Clean up temporary files
# rm $MANIFEST

# # Verify the JAR
# echo "🔍 Verifying JAR structure..."
# jar tf $JAR_FILE | head -10

# # Done!
# echo -e "\n✅ Build complete! Run it with:"
# echo "java -jar $JAR_FILE"
# echo -e "\nOr with progress display:"
# echo "java -jar $JAR_FILE | tee output.log"