package com.nicolas.stocksapi.core;

import io.vavr.control.Either;

public interface IUsecase<I, O> {
    public abstract Either<Exception, O> call(I params);
}
