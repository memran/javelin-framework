package io.javelin.support;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

public final class Image {
    private Image() {
    }

    public static Handle of(Path path) {
        return new Handle(path);
    }

    public static boolean isImage(Path path) {
        return info(path).isPresent();
    }

    public static Optional<Info> info(Path path) {
        Objects.requireNonNull(path, "path");
        try (var stream = Files.newInputStream(path);
             ImageInputStream input = ImageIO.createImageInputStream(stream)) {
            if (input == null) {
                return Optional.empty();
            }
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            if (!readers.hasNext()) {
                return Optional.empty();
            }
            ImageReader reader = readers.next();
            try {
                reader.setInput(input, true, true);
                return Optional.of(new Info(
                        reader.getWidth(0),
                        reader.getHeight(0),
                        reader.getFormatName().toLowerCase(Locale.ROOT)
                ));
            } finally {
                reader.dispose();
            }
        } catch (IOException exception) {
            return Optional.empty();
        }
    }

    public static Dimensions dimensions(Path path) {
        return info(path)
                .map(Info::dimensions)
                .orElseThrow(() -> new IllegalArgumentException("Unable to read image dimensions from " + path));
    }

    public static BufferedImage read(Path path) {
        Objects.requireNonNull(path, "path");
        try {
            BufferedImage image = ImageIO.read(path.toFile());
            if (image == null) {
                throw new IllegalArgumentException("Unable to read image from " + path);
            }
            return image;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read image from " + path, exception);
        }
    }

    public static final class Handle {
        private final Path path;

        private Handle(Path path) {
            this.path = Objects.requireNonNull(path, "path");
        }

        public Path path() {
            return path;
        }

        public boolean exists() {
            return Files.exists(path);
        }

        public boolean isImage() {
            return Image.isImage(path);
        }

        public Optional<Info> info() {
            return Image.info(path);
        }

        public Dimensions dimensions() {
            return Image.dimensions(path);
        }

        public int width() {
            return info().map(Info::width).orElseThrow(() -> new IllegalArgumentException("Unable to read image dimensions from " + path));
        }

        public int height() {
            return info().map(Info::height).orElseThrow(() -> new IllegalArgumentException("Unable to read image dimensions from " + path));
        }

        public String format() {
            return info().map(Info::format).orElseThrow(() -> new IllegalArgumentException("Unable to read image metadata from " + path));
        }

        public BufferedImage read() {
            return Image.read(path);
        }

        public Handle when(boolean condition, UnaryOperator<Handle> callback) {
            Objects.requireNonNull(callback, "callback");
            return condition ? Objects.requireNonNull(callback.apply(this), "callback result") : this;
        }

        public Handle unless(boolean condition, UnaryOperator<Handle> callback) {
            return when(!condition, callback);
        }
    }

    public record Info(int width, int height, String format) {
        public Info {
            Objects.requireNonNull(format, "format");
        }

        public Dimensions dimensions() {
            return new Dimensions(width, height);
        }
    }

    public record Dimensions(int width, int height) {
    }
}
